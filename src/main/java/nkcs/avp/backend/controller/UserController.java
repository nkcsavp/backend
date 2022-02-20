package nkcs.avp.backend.controller;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.UserService;
import nkcs.avp.backend.util.EncryptionUtil;
import nkcs.avp.backend.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify")
    Boolean verify(@RequestParam String mail, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (mail.matches("^\\w+([-+.]\\w+)*@mail.nankai.edu.cn$") || mail.matches("^\\w+([-+.]\\w+)*@nankai.edu.cn$")) {
            // Send mail and get verify code
            String code = "12345";
            session.setAttribute("verify", code);
            session.setAttribute("mail", mail);
            return true;
        }
        return false;
    }

    @GetMapping("/login")
    Boolean logIn(@RequestParam String mail, @RequestParam String pwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.getUserByName(mail);
        if (user != null && user.getPwd().equals(EncryptionUtil.getResult(pwd + mail))) {
            session.setAttribute("user", user);
            SessionUtil.sessionSave.put(mail,session.getId());
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    Boolean logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        return true;
    }

    @PostMapping("/register")
    String register(@RequestParam String mail, @RequestParam String pwd, @RequestParam String verify, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("verify") == null || session.getAttribute("mail") == null) {
            return "[ERROR]Verify Before Register";
        }
        if (!session.getAttribute("verify").equals(verify) || !session.getAttribute("mail").equals(mail)) {
            return "[ERROR]Invalid Verify Code";
        }
        if (pwd.length() >= 6 && pwd.length() <= 16) {
            pwd = pwd + mail;
            pwd = EncryptionUtil.getResult(pwd);
            try {
                userService.addUser(new User(mail, pwd));
            } catch (Exception e) {
                return "[ERROR]Mail has been Used";
            }
            session.removeAttribute("mail");
            session.removeAttribute("verify");
            return "[INFO]Success";
        }
        return "[ERROR]Password Length Should be in 6~16";
    }
}
