package nkcs.avp.backend.mapper;

import nkcs.avp.backend.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserByName(String name);
    int addUser(User user);
    int updateStatus(User user);
}
