package com.example.hellowebflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<?> helloRouterFunction() {
        return RouterFunctions.route(
                        RequestPredicates.GET("/hello"),
                        request -> ServerResponse.ok().body(Mono.just("Hello World!"), String.class))
                .andRoute(RequestPredicates.GET("/bye"),
                        request -> ServerResponse.ok().body(Mono.just("bye bye"), String.class));
    }

}
