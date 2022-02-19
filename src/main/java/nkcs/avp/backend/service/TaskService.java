package nkcs.avp.backend.service;

import nkcs.avp.backend.domain.Task;

import java.util.ArrayList;

public interface TaskService {
    int addTask(Task task);
    int updateTask(Task task);
    ArrayList<Task> getAllTaskById(long uid);
}
