package com.example.ingredientclient.config;

import com.example.ingredientclient.IngredientClientApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class IngredientServiceServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IngredientClientApplication.class);
    }
}
