package com.example.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FluxTests {

    @Test
    public void fluxCreateTest() {
//        Flux<String> just = Flux.just("A", "B", "C", "D");
        Flux.just("A", "B", "C", "D")
                .subscribe(s -> System.out.println("Here's some letter:" + s));
    }

    @Test
    public void fluxCreateTest_expect() {
        Flux<String> flux = Flux.just("A", "B", "C", "D");
        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .verifyComplete();
    }

    @Test
    public void fluxCreateTest_array() {
        String[] strings = {"A", "B", "C", "D"};
        Flux<String> stringFlux = Flux.fromArray(strings);
        StepVerifier.create(stringFlux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .verifyComplete();
    }

    @Test
    public void fluxCreateTest_arrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        Flux<String> stringFlux = Flux.fromIterable(arrayList);
        StepVerifier.create(stringFlux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .verifyComplete();
    }

    @Test
    public void fluxCreateTest_stream() {
        Stream<String> stream = Stream.of("A", "B", "C", "D");
        Flux<String> stringFlux = Flux.fromStream(stream);
        StepVerifier.create(stringFlux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectNext("D")
                .verifyComplete();
    }

    @Test
    public void fluxCreateTest_range() {
        Flux<Integer> range = Flux.range(0, 5);
        StepVerifier.create(range)
                .expectNext(0)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void fluxCreateTest_interval() {
        Flux<Long> take = Flux.interval(Duration.ofSeconds(1)).take(5);
        StepVerifier.create(take)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }
}
