package nkcs.avp.backend.controller;

import djudger.Allocator;
import djudger.entity.LangEnum;
import io.swagger.annotations.Api;
import nkcs.avp.backend.domain.Source;
import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.SourceService;
import nkcs.avp.backend.service.TaskService;
import nkcs.avp.backend.service.UserService;
import nkcs.avp.backend.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CodeController {

    static {
        Allocator.init();
    }

    private TaskService taskService;
    private SourceService sourceService;
    private UserService userService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setSourceService(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/submit")
    String submitCode(@RequestParam String code, @RequestParam String sample, @RequestParam String mode, @RequestParam String lang, HttpServletRequest request){
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if(user == null) return "403";

        Task task = new Task(user.getId(), new Timestamp(new Date().getTime()),lang,mode,sample);
        String fileName = user.getId() + "_" + task.getTime().getTime();
        Source source = new Source(code,fileName);

        sourceService.addSource(source);
        task.setSid(fileName);
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

        String animation = Allocator.runCode(LangEnum.Java, commands, fileName, code);

        if(animation.equals("TLE")){
            task.setStatus(2);
        }else{
            task.setAnimation(animation);
            task.setStatus(1);
        }
        taskService.updateTask(task);
        return animation;
    }
}
