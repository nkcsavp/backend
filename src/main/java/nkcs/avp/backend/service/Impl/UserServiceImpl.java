package nkcs.avp.backend.service.Impl;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.mapper.UserMapper;
import nkcs.avp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
