package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

    public static void main(String[] args) {
        SpringApplication.run(RestExamples.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner fetchIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- GET -------------------------");
            log.info("GETTING INGREDIENT BY ID");
            log.info("Ingredient 1:  {}", tacoCloudClient.getIngredientById1("CHED"));
            log.info("Ingredient 2:  {}", tacoCloudClient.getIngredientById2("CHED"));
            log.info("Ingredient 3:  {}", tacoCloudClient.getIngredientById3("CHED"));
            log.info("Ingredient 4:  {}", tacoCloudClient.getIngredientById4("CHED"));
        };
    }
}
