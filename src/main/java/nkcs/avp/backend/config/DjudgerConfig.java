package nkcs.avp.backend.config;

import djudger.AllocatorFactory;
import djudger.Config;
import djudger.LangConfig;
import djudger.allocator.Allocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DjudgerConfig {
    @Value("${djudger.docker-socket}")
    private String dockerSocket;

    @Value("${djudger.code-path}")
    private String codePath;

    @Value("${djudger.seccomp}")
    private Boolean seccomp;

    @Value("${djudger.seccomp-path}")
    private String seccompPath;

    public Config getConfig(){
        Config config = new Config();
        if(seccomp){
            config.enableSeccomp(seccompPath);
        }
        return config
                .configDocker(dockerSocket)
                .configClassicAllocator(1800, 4, 2)
                .configCodePath(codePath)
                .addDefaultLang();
    }

    @Bean
    public Allocator getAllocator(){
        return AllocatorFactory.build(getConfig());
    }

}
