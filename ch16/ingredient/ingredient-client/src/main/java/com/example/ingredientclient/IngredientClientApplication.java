package com.example.ingredientclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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

    @Component
    public static class MyInfo implements InfoContributor {
        @Override
        public void contribute(Info.Builder builder) {
            Map<String, String> info = new HashMap<>();
            info.put("name", "name");
            info.put("key", "value");
            builder.withDetail("author", info);
        }
    }
}
