package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;
import tacos.model.Ingredient;
import tacos.model.Taco;
import tacos.web.api.model.TacoModel;

import java.net.URI;
import java.util.Collection;
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
    public Traverson traverson() {
        return new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
    }

    //    @Bean
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

    //    @Bean
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

        };
    }

    //    @Bean
    public CommandLineRunner postIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- POST -------------------------");
            Ingredient ingredient1 = tacoCloudClient.createIngredient1(new Ingredient("cre1", "cre1 name", Ingredient.Type.VEGGIES));
            log.info("ingredient1:  {}", ingredient1);

            URI ingredient2 = tacoCloudClient.createIngredient2(new Ingredient("cre2", "cre2 name", Ingredient.Type.VEGGIES));
            log.info("ingredient2:  {}", ingredient2);

            Ingredient ingredient3 = tacoCloudClient.createIngredient3(new Ingredient("cre3", "cre3 name", Ingredient.Type.VEGGIES));
            log.info("ingredient3:  {}", ingredient3);
        };
    }

    //    @Bean
    public CommandLineRunner deleteIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- DELETE -------------------------");
            Ingredient crea1 = tacoCloudClient.getIngredientById1("crea");
            log.info("crea1:  {}", crea1);
            tacoCloudClient.deleteIngredient("crea");
            try {
                Ingredient crea2 = tacoCloudClient.getIngredientById1("crea");
                log.info("crea2:  {}", crea2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    //    @Bean
    public CommandLineRunner allIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- ALL INGREDIENTS -------------------------");
            Collection<Ingredient> ingredients = tacoCloudClient.allIngredients();
            ingredients.forEach(ingredient -> log.info("ingredient = 【{}】", ingredient));
        };
    }

    @Bean
    public CommandLineRunner recent(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- RECENT -------------------------");
            Collection<TacoModel> recent = tacoCloudClient.recent();
            recent.forEach(taco -> log.info("ingredient = 【{}】", taco));
        };
    }
}
