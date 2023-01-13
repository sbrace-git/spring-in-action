package com.example.ingredientclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@EnableHystrix
@SpringBootApplication
public class IngredientClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngredientClientApplication.class, args);
    }

    @Autowired
    private Environment environment;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            String email = environment.getProperty("info.contact.email");
            System.out.println("email = " + email);
            String phone = environment.getProperty("info.contact.phone");
            System.out.println("phone = " + phone);
        };
    }
}
