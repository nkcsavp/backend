package nkcs.avp.backend.controller;

import djudger.Allocator;
import djudger.entity.LangEnum;
import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.TaskService;
import nkcs.avp.backend.util.CodeUtil;
import nkcs.avp.backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RestController
public class CodeController {

    static {
        Allocator.init();
    }

    private TaskService taskService;

    private static List<String> modes = Arrays.asList("tree", "array", "graph");

    private static List<String> langs = Arrays.asList("java", "python", "cpp");

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/submit")
    ResponseEntity<String> submitCode(@RequestBody String code, @RequestParam String mode, @RequestParam String lang, @RequestParam String sample, @RequestParam String tag, String relation, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (!modes.contains(mode)) {
            return ResponseUtil.Response(400, "Mode Parameter not Supported");
        }

        if (!langs.contains(lang)) {
            return ResponseUtil.Response(400, "Lang Parameter not Supported");
        }

        if (!sample.matches("([0-9]+,)*([0-9]+)")) {
            return ResponseUtil.Response(400, "Wrong Sample Format");
        }

        if (code.length() > 40000) {
            return ResponseUtil.Response(400, "Code is Too Long");
        }


        if (mode.equals("graph")) {
            if (relation == null || !relation.matches("([01],)*[01]")) {
                return ResponseUtil.Response(400, "Graph Relation Illegal");
            } else {
                sample = sample + "&" + relation;
                code = code.replace("$(relation)", relation);
            }
        }

        Task task = new Task(user.getId(), sample, code, lang, mode, tag);
        String identifier = user.getId() + "_" + task.getTime().getTime();
        task.setIdentifier(identifier);

        taskService.addTask(task);

        code = CodeUtil.codes.get(mode + "_" + lang).replace("$(code)", code).replace("$(sample)", sample);

        LangEnum langEnum;
        if (lang.equals("java")) {
            langEnum = LangEnum.Java;
        } else if (lang.equals("python")) {
            langEnum = LangEnum.Python;
        } else {
            langEnum = LangEnum.CPP;
        }

        String[] result = Allocator.runCode(langEnum, CodeUtil.commands.get(lang), identifier, code);

        String pattern = "(([\\w]+\\((([\\d]+,)*[\\d]+)*\\)):)*[\\w]+\\((([\\d]+,)*[\\d]+)*\\)";

        if (result[0] == null) {
            task.setStatus(2);
            taskService.updateTask(task);
            return ResponseUtil.Response(400, "Code Run Timeout");
        } else if (result[1].trim().length() != 0) {
            task.setStatus(3);
            taskService.updateTask(task);
            return ResponseUtil.Response(400, "Code Run Error");
        } else if (result[0].length() == 0) {
            task.setStatus(4);
            taskService.updateTask(task);
            return ResponseUtil.Response(400, "Empty Animation");
        } else if (!result[0].matches(pattern)) {
            task.setStatus(5);
            taskService.updateTask(task);
            return ResponseUtil.Response(400, "Wrong Content in stdout");
        } else {
            task.setStatus(1);
            task.setAnimation(result[0]);
            taskService.updateTask(task);
        }
        return ResponseUtil.Response(result[0]);
    }
}
