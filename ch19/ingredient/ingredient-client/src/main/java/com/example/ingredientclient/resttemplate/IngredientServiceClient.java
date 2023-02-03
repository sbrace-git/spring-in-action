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

    private static int count = 0;

    @HystrixCommand(fallbackMethod = "getDefaultIngredientById",
            commandProperties = {
                    @HystrixProperty(
                            name = "circuitBreaker.requestVolumeThreshold",
                            value = "30"),
                    @HystrixProperty(
                            name = "circuitBreaker.errorThresholdPercentage",
                            value = "25"),
                    @HystrixProperty(
                            name = "metrics.rollingStats.timeInMilliseconds",
                            value = "30000"),
                    @HystrixProperty(
                            name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "60000")
            })
    public Ingredient getIngredientById(String ingredientId) {
        count++;
        System.out.println("getIngredientById count = " + count);
        if (count > 10 && count < 60) {
            throw new RuntimeException("error");
        }
        return rest.getForObject(
                "http://ingredient-service/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

    public Long count() {
        return rest.getForObject(
                "http://ingredient-service/ingredients/count",
                Long.class);
    }

    public Ingredient getDefaultIngredientById(String ingredientId) {
        count++;
        System.out.println("getDefaultIngredientById count = " + count);
        return new Ingredient(
                "FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
    }

    @HystrixCommand(fallbackMethod = "getDefaultIngredients")
    public Iterable<Ingredient> getAllIngredients() {
//        int i = 1 / 0;
        Ingredient[] ingredients = rest.getForObject(
                "http://ingredient-service/ingredients", Ingredient[].class);
        return Arrays.asList(ingredients);
    }

    @HystrixCommand(fallbackMethod = "getDefaultIngredients2",
            commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
                    @HystrixProperty(name = "execution.timeout.enabled", value = "false")
            }

    )
    private Iterable<Ingredient> getDefaultIngredients() throws InterruptedException {
//        Thread.sleep(1000);
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
