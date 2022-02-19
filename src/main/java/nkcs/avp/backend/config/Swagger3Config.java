package nkcs.avp.backend.config;/*
 *    高薪启蒙 csdn学院地址：https://edu.csdn.net/lecturer/1141?spm=1002.2001.3001.4144
 *    Array老师：https://edu.csdn.net/course/detail/29600
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger3Config implements WebMvcConfigurer {

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("nkcs.avp.backend.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Test")
                .version("1.0")
                .build();
    }
}

