package nkcs.avp.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("nkcs.avp.backend.mapper")
@ServletComponentScan("nkcs.avp.backend.filter")
public class AvpBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvpBackendApplication.class, args);
    }

}
