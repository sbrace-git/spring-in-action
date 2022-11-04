package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Slf4j
@SpringBootApplication
public class TacoCloudMvnModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudMvnModulesApplication.class, args);
    }

    @Bean
    @Profile("dev")
    Object bean1() {
        log.info("bean1");
        return new Object();
    }

    @Bean
    @Profile("!prod")
    Object bean2() {
        log.info("bean2");
        return new Object();
    }

    @Bean
    @Profile({"test","prod"})
    Object bean3() {
        log.info("bean3");
        return new Object();
    }
}
