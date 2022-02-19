package nkcs.avp.backend.service.Impl;

import nkcs.avp.backend.domain.Task;
import nkcs.avp.backend.mapper.TaskMapper;
import nkcs.avp.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskMapper taskMapper;

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public int addTask(Task task) {
        return taskMapper.addTask(task);
    }

    @Override
    public int updateTask(Task task) {
        return taskMapper.updateTask(task);
    }

    @Override
    public ArrayList<Task> getAllTaskById(long uid) {
        return taskMapper.getAllTaskById(uid);
    }
}
