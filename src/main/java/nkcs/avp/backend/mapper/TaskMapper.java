package nkcs.avp.backend.mapper;

import nkcs.avp.backend.domain.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TaskMapper {
    int addTask(Task task);
    int updateTask(Task task);
    ArrayList<Task> getAllTaskById(long uid);
}
