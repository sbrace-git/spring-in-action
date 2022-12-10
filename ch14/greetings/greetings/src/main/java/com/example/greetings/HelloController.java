package com.example.greetings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private GreetingsProperties greetingsProperties;

    public HelloController(GreetingsProperties greetingsProperties) {
        this.greetingsProperties = greetingsProperties;
    }

    @RequestMapping("/hello")
    public String hello() {
        return greetingsProperties.getMessage() + ", " + greetingsProperties.getName() + ", " + greetingsProperties.getProfile();
    }
}
