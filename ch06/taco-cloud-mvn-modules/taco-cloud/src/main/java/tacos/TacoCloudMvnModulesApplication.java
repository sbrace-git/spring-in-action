package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

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

    // To avoid 404s when using Angular HTML 5 routing
    @Bean
    ErrorViewResolver supportPathBasedLocationStrategyWithoutHashes() {
        return (request, status, model) -> status == HttpStatus.NOT_FOUND
                ? new ModelAndView("index.html", Collections.emptyMap(), HttpStatus.OK)
                : null;
    }
}
