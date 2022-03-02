package nkcs.avp.backend.service;

import nkcs.avp.backend.domain.Task;

import java.util.ArrayList;
import java.util.Map;

public interface TaskService {
    int addTask(Task task);
    int updateTask(Task task);
    ArrayList<Task> selectCondition(Map<String,Object> map);
    int deleteById(String identifier);
    Task getById(String identifier);
}
