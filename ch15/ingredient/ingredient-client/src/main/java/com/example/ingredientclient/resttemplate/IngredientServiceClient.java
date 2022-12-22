package com.example.ingredientclient.resttemplate;

import com.example.ingredientclient.Ingredient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Conditional(NotFeignAndNotWebClientCondition.class)
public class IngredientServiceClient {

    private RestTemplate rest;

    public IngredientServiceClient(@LoadBalanced RestTemplate rest) {
        this.rest = rest;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject(
                "http://ingredient-service/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    @HystrixCommand(fallbackMethod = "getDefaultIngredients")
    public Iterable<Ingredient> getAllIngredients() {
        int i = 1 / 0;
        Ingredient[] ingredients = rest.getForObject(
                "http://ingredient-service/ingredients", Ingredient[].class);
        return Arrays.asList(ingredients);
    }

    @HystrixCommand(fallbackMethod = "getDefaultIngredients2",
            commandProperties =
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"))
    private Iterable<Ingredient> getDefaultIngredients() throws InterruptedException {
        Thread.sleep(1000);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(
                "FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        ingredients.add(new Ingredient(
                "GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredients.add(new Ingredient(
                "CHED", "Shredded Cheddar", Ingredient.Type.CHEESE));
        return ingredients;
    }

    private Iterable<Ingredient> getDefaultIngredients2() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(
                "FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        return ingredients;
    }
}
