package com.example.greetings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private GreetingsProperties greetingsProperties;
    private ApplicationProperties applicationProperties;

    public HelloController(GreetingsProperties greetingsProperties, ApplicationProperties applicationProperties) {
        this.greetingsProperties = greetingsProperties;
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping("/hello")
    public String hello() {
        return greetingsProperties.getMessage() + ", " + greetingsProperties.getName() + ", " + greetingsProperties.getProfile() + "," + applicationProperties.getTest();
    }
}
