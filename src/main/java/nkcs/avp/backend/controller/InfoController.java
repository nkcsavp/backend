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
import java.util.HashMap;
import java.util.Map;

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
    ArrayList<Task> getTask(String tag, String lang, String mode, Integer status, @RequestParam Integer start, @RequestParam Integer length, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("uid", user.getId());
        map.put("start", start);
        map.put("length", length);
        if (tag != null) map.put("tag", "%" + tag + "%");
        if (lang != null) map.put("lang", lang);
        if (mode != null) map.put("mode", mode);
        if (status != null) map.put("status", status);
        return taskService.selectCondition(map);
    }

    @GetMapping("/tasks/remove")
    ResponseEntity<String> removeTask(@RequestParam String identifier) {
        if (taskService.deleteById(identifier) == 1) {
            return ResponseUtil.Response("Task Remove Successfully");
        }
        return ResponseUtil.Response(403, "Task Not Exist");
    }

    @PostMapping("/updatepwd")
    ResponseEntity<String> updatePwd(@RequestParam String pwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        pwd = pwd + user.getMail();
        pwd = EncryptionUtil.getResult(pwd);
        user.setPwd(pwd);
        userService.updatePwd(user);
        return ResponseUtil.Response("Password Update Successfully");
    }

}
