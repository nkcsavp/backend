package nkcs.avp.backend.controller;

import djudger.DJudgerException;
import djudger.StatusEnum;
import djudger.allocator.Allocator;
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
import java.util.concurrent.TimeUnit;

@RestController
public class CodeController {

    private TaskService taskService;

    private Allocator allocator;

    private static List<String> modes = Arrays.asList("tree", "array", "graph");

    private static List<String> langs = Arrays.asList("java", "python", "cpp");

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setAllocator(Allocator allocator) {
        this.allocator = allocator;
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

        String finalCode = CodeUtil.codes.get(mode + "_" + lang).replace("$(code)", code).replace("$(sample)", sample);

        if (mode.equals("graph")) {
            if (relation == null || !relation.matches("([01],)*[01]")) {
                return ResponseUtil.Response(400, "Graph Relation Illegal");
            } else {
                finalCode = finalCode.replace("$(relation)", relation);
                sample = sample + "&" + relation;
            }
        }

        Task task = new Task(user.getId(), sample, code, lang, mode, tag);
        String identifier = user.getId() + "_" + task.getTime().getTime();
        task.setIdentifier(identifier);

        taskService.addTask(task);

        djudger.Task result = null;
        try {
            result = allocator.runCode(lang.equals("python") ? "py" : lang, CodeUtil.commands.get(lang),2000, TimeUnit.MILLISECONDS, identifier, finalCode);
        } catch (DJudgerException e) {
            e.printStackTrace();
            return ResponseUtil.Response(500, e.getMessage());
        }

        String pattern = "(([\\w]+\\((([\\d]+,)*[\\d]+)*\\)):)*[\\w]+\\((([\\d]+,)*[\\d]+)*\\)";

        StatusEnum status = result.getStatus();

        if (status == StatusEnum.TIMEOUT) {
            task.setStatus(2);
            return ResponseUtil.Response(400, "Code Run Timeout");
        } else if (status == StatusEnum.ERROR || result.getStderr().trim().length() != 0) {
            task.setStatus(3);
            return ResponseUtil.Response(400, "Code Run Error");
        } else if (result.getStdout().length() == 0) {
            task.setStatus(4);
            return ResponseUtil.Response(400, "Empty Animation");
        } else if (!result.getStdout().matches(pattern)) {
            task.setStatus(5);
            return ResponseUtil.Response(400, "Wrong Content in stdout");
        } else {
            task.setStatus(1);
            task.setAnimation(result.getStdout());
        }
        taskService.updateTask(task);

        return ResponseUtil.Response(result.getStdout());
    }
}
