package nkcs.avp.backend.controller;

import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/info")
public class InfoController {
    TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    @ResponseBody
    ArrayList<Task> getAllTask(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return taskService.getAllTaskById(user.getId());
    }

}
