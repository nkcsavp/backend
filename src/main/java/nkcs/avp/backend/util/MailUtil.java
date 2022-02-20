package nkcs.avp.backend.util;

import djudger.util.DockerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class MailUtil {

    JavaMailSender mailSender;

    static String title = "Verify Code from Algorithm Visualization Platform";

    static String body;

    static {
        InputStream inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream("static/code.html");
        body = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendVerifyCode(String mail) {
        String code = verifyCodeGenerator();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        try {
            message.setFrom("verifycode_nicer@qq.com");
            message.setTo(mail);
            message.setSubject(title);
            message.setText(body.replace("$(verify)",code),true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
        return code;
    }

    private static String verifyCodeGenerator(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<6;i++){
            stringBuilder.append(Math.round(Math.random() * 9));
        }

        return stringBuilder.toString();
    }
}