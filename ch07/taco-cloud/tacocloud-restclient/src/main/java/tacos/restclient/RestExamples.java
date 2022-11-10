package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tacos.model.Ingredient;

import java.net.URI;
import java.util.List;

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
            log.info("GETTING ALL INGREDIENTS");
            List<Ingredient> ingredients = tacoCloudClient.getAllIngredients();
            log.info("All ingredients:");
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner putIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = tacoCloudClient.getIngredientById1("LETC");
            log.info("BEFORE:  {}", before);

            tacoCloudClient.putIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
            Ingredient after = tacoCloudClient.getIngredientById1("LETC");
            log.info("AFTER:  {}", after);


            tacoCloudClient.putIngredient(new Ingredient("crea", "create name", Ingredient.Type.VEGGIES));
            Ingredient crea = tacoCloudClient.getIngredientById1("crea");
            log.info("crea:  {}", crea);

//            Ingredient ingredient1 = tacoCloudClient.createIngredient1(new Ingredient("creat1", "creat1 name", Ingredient.Type.VEGGIES));
//            Ingredient ingredient11 = tacoCloudClient.getIngredientById1("creat1");
//            log.info("ingredient1:  {}", ingredient1);
//            log.info("ingredient11:  {}", ingredient11);
//
//
//            URI ingredient2 = tacoCloudClient.createIngredient2(new Ingredient("creat2", "creat2 name", Ingredient.Type.VEGGIES));
//            Ingredient ingredient22 = tacoCloudClient.getIngredientById2("creat2");
//            log.info("ingredient2:  {}", ingredient2);
//            log.info("ingredient22:  {}", ingredient22);
//
//
//            Ingredient ingredient3 = tacoCloudClient.createIngredient3(new Ingredient("creat3", "creat3 name", Ingredient.Type.VEGGIES));
//            Ingredient ingredient33 = tacoCloudClient.getIngredientById3("creat3");
//            log.info("ingredient3:  {}", ingredient3);
//            log.info("ingredient33:  {}", ingredient33);

        };
    }
}
