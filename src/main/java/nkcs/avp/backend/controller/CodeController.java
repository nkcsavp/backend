package nkcs.avp.backend.controller;

import djudger.Allocator;
import djudger.entity.LangEnum;
import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.TaskService;
import nkcs.avp.backend.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CodeController {

    static {
        Allocator.init();
    }

    private TaskService taskService;

    private static List<String> modes = Arrays.asList("tree","array");

    private static List<String> langs = Arrays.asList("java");

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/submit")
    String submitCode(@RequestParam String code, @RequestParam String sample, @RequestParam String mode, @RequestParam String lang, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(!modes.contains(mode)){
            return "[ERROR]Mode Parameter not Supported.";
        }

        if(!langs.contains(lang)){
            return "[ERROR]Lang Parameter not Supported.";
        }

        if(!sample.matches("([0-9]+,)*([0-9]+)")){
            return "[ERROR]Wrong Sample Format.";
        }

        if(code.length() > 40000){
            return "[ERROR]Code is Too Long.";
        }

        Task task = new Task(user.getId(),sample,code,lang,mode);
        String identifier = user.getId() + "_" + task.getTime().getTime();
        task.setIdentifier(identifier);

        taskService.addTask(task);

        List<String> commands = new ArrayList<>();
        commands.add("cd $(directory)");
        commands.add("javac $(code)");
        commands.add("java Main");

        if(mode.equals("array")){
            code = CodeUtil.array.replace("$(code)",code).replace("$(sample)",sample);
        }
        else if(mode.equals("tree")){
            code = CodeUtil.tree.replace("$(code)",code).replace("$(sample)",sample);
        }

        String animation = Allocator.runCode(LangEnum.Java, commands, identifier, code);

        if(animation == null){
            task.setStatus(2);
            taskService.updateTask(task);
            return "ERR";
        }else{
            task.setAnimation(animation);
            task.setStatus(1);
            taskService.updateTask(task);
        }
        return animation;
    }
}
