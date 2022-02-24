package nkcs.avp.backend.mapper;

import nkcs.avp.backend.domain.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface TaskMapper {
    int addTask(Task task);
    int updateTask(Task task);
    ArrayList<Task> selectCondition(Map<String,Object> map);
    int deleteById(String identifier);
}
