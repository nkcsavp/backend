package nkcs.avp.backend.controller;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.service.UserService;
import nkcs.avp.backend.util.EncryptionUtil;
import nkcs.avp.backend.util.MailUtil;
import nkcs.avp.backend.util.ResponseUtil;
import nkcs.avp.backend.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@RestController
public class UserController {
    private UserService userService;
    private MailUtil mailUtil;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailUtil(MailUtil mailUtil) {
        this.mailUtil = mailUtil;
    }

    @GetMapping("/verify")
    ResponseEntity<String> verify(@RequestParam String mail, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long now = new Date().getTime();
        if (mail.matches("^\\w+([-+.]\\w+)*@[\\w\\.]+.edu.cn$")) {
            if(session.getAttribute("last") == null){
                session.setAttribute("last",now);
            }
            else{
                Long last = (Long) session.getAttribute("last");
                if(now - last < 60 * 1000){
                    return ResponseUtil.Response(403,"Too Frequent Requests for Code");
                }
                else {
                    session.setAttribute("last",now);
                }
            }
            String code = mailUtil.sendVerifyCode(mail);
            session.setAttribute("verify", code);
            session.setAttribute("mail", mail);
            return ResponseUtil.Response("Verify Code Send Successfully");
        }
        return ResponseUtil.Response(403,"Email Address not Allowed");
    }

    @GetMapping("/login")
    ResponseEntity<String> logIn(@RequestParam String mail, @RequestParam String pwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.getUserByName(mail);
        if (user != null && user.getPwd().equals(EncryptionUtil.getResult(pwd + mail))) {
            session.setAttribute("user", user);
            SessionUtil.sessionSave.put(mail,session.getId());
            return ResponseUtil.Response("Log In Successfully");
        }
        return ResponseUtil.Response(403,"Wrong Password or Mail Address");
    }

    @GetMapping("/logout")
    ResponseEntity<String> logOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            session.removeAttribute(names.nextElement());
        }
        return ResponseUtil.Response("Log Out Successfully");
    }

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestParam String mail, @RequestParam String pwd, @RequestParam String verify, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("verify") == null || session.getAttribute("mail") == null) {
            return ResponseUtil.Response(400,"Verify Before Register");
        }
        if (!session.getAttribute("verify").equals(verify) || !session.getAttribute("mail").equals(mail)) {
            return ResponseUtil.Response(400,"Invalid Verify Code");
        }
        pwd = pwd + mail;
        pwd = EncryptionUtil.getResult(pwd);
        try {
            userService.addUser(new User(mail, pwd));
        } catch (Exception e) {
            return ResponseUtil.Response(400,"Mail Address has been Used");
        }
        session.removeAttribute("mail");
        session.removeAttribute("verify");
        return ResponseUtil.Response("Register Successfully");
    }

    @GetMapping("/lostVerify")
    ResponseEntity<String> lostPwd(@RequestParam String mail, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.getUserByName(mail);
        if(user == null){
            return ResponseUtil.Response(404,"User Not Exist");
        }
        Long now = new Date().getTime();
        if(session.getAttribute("last_lost") == null){
            session.setAttribute("last_lost",now);
        }
        else{
            Long last = (Long) session.getAttribute("last_lost");
            if(now - last < 60 * 1000){
                return ResponseUtil.Response(403,"Too Frequent Requests for Code");
            }
            else {
                session.setAttribute("last_lost",now);
            }
        }
        String code = mailUtil.sendVerifyCode(mail);
        session.setAttribute("verify_lost", code);
        session.setAttribute("mail_lost", mail);
        return ResponseUtil.Response("Verify Code Send Successfully");
    }

    @PostMapping("/lostUpdate")
    ResponseEntity<String> updatePwd(@RequestParam String mail, @RequestParam String pwd, @RequestParam String verify, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("verify_lost") == null || session.getAttribute("mail_lost") == null) {
            return ResponseUtil.Response(400,"Verify Before Update");
        }
        if (!session.getAttribute("verify_lost").equals(verify) || !session.getAttribute("mail_lost").equals(mail)) {
            return ResponseUtil.Response(400,"Invalid Verify Code");
        }
        pwd = pwd + mail;
        pwd = EncryptionUtil.getResult(pwd);
        User user = userService.getUserByName(mail);
        user.setPwd(pwd);
        userService.updatePwd(user);
        session.removeAttribute("mail_lost");
        session.removeAttribute("verify_lost");
        return ResponseUtil.Response("Update Successfully");
    }
}
