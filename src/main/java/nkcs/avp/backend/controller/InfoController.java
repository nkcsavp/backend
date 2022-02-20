package nkcs.avp.backend.controller;

import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.TaskService;
import nkcs.avp.backend.service.UserService;
import nkcs.avp.backend.util.EncryptionUtil;
import nkcs.avp.backend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/info")
public class InfoController {
    TaskService taskService;
    UserService userService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tasks")
    @ResponseBody
    ArrayList<Task> getAllTask(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return taskService.getAllTaskById(user.getId());
    }

    @PostMapping("/updatepwd")
    ResponseEntity<String> updatePwd(@RequestParam String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (pwd.length() >= 6 && pwd.length() <= 16) {
            pwd = pwd + user.getMail();
            pwd = EncryptionUtil.getResult(pwd);
            user.setPwd(pwd);
            userService.updatePwd(user);
            return ResponseUtil.Response("Password Update Successfully");
        }
        return ResponseUtil.Response(400,"Password Length Should be in 6~16");
    }

}
