package com.example.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {


    @Test
    public void monoTest() {
        Mono.just("name")
                .map(String::toUpperCase)
                .map(name -> "Hello," + name + "!")
                .subscribe(System.out::println);
    }

}
