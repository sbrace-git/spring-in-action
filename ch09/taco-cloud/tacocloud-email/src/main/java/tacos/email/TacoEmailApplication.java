package tacos.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tacos.email.properties.ApiProperties;

import java.util.Collections;

@Slf4j
@SpringBootApplication
public class TacoEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoEmailApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
    public CommandLineRunner post(RestTemplate restTemplate, ApiProperties apiProperties) {
        return args -> {
            log.info("post");
            log.info("apiProperties = {}", apiProperties);
            restTemplate.postForObject(apiProperties.getUrl(), Collections.singletonMap("key", "value"), String.class);
        };
    }
}
