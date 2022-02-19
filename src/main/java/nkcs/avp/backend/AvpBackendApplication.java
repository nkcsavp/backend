package nkcs.avp.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("nkcs.avp.backend.mapper")
public class AvpBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AvpBackendApplication.class, args);
    }

}
