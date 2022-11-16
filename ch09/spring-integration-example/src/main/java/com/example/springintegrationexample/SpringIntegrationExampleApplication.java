package com.example.springintegrationexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class SpringIntegrationExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationExampleApplication.class, args);
    }

    @Bean
    public CommandLineRunner writeData(FileWriterGateway fileWriterGateway, Environment environment) {
        return args -> {
            String[] activeProfiles = environment.getActiveProfiles();
            log.info("activeProfiles = {}", (Object) activeProfiles);
            if (activeProfiles.length > 0) {
                fileWriterGateway.writeToFile("simple.txt", "Hello, Spring Integration! (" + activeProfiles[0] + ")");
            } else {
                log.error("No active profile set. Should set active profile to one of xmlconfig, javaconfig, or javadsl.");
            }
        };
    }

}
