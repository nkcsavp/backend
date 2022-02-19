package nkcs.avp.backend.controller;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.UserService;
import nkcs.avp.backend.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    Boolean logIn(@RequestParam String name, @RequestParam String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (!name.equals("") && !pwd.equals("")) {
            User user = userService.getUserByName(name);
            if (user != null && user.getPwd().equals(EncryptionUtil.getResult(pwd + name))) {
                session.setAttribute("user", user);
                return true;
            }
        }
        return false;
    }

    @GetMapping("/logout")
    Boolean logOut(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return true;
    }

    @PostMapping("/register")
    Boolean register(@RequestParam String name, @RequestParam String pwd){
        int arrow = 0;
        if (!pwd.equals("") && !name.equals("")) {
            pwd = pwd + name;
            pwd = EncryptionUtil.getResult(pwd);
            try {
                arrow = userService.addUser(new User(name, pwd));
            }
            catch (Exception e){
                return false;
            }
        }
        return arrow == 1;
    }


}
