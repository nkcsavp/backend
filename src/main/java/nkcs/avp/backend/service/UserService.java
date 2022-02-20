package nkcs.avp.backend.service;


import nkcs.avp.backend.domain.User;

public interface UserService {
    User getUserByName(String name);
    int addUser(User user);
    int updatePwd(User user);
}
