package com.shieldteq.reactor.demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class FluxSinkTest {
    public static void main(String[] args) {
        generateName();
    }

    private static void generateName() {
        Flux.<String, List<String>>generate(
                () -> List.of("a", "b", "c"),
                (x, y) -> {
                    System.out.println(x);
                    if (x.isEmpty()) {
                        y.complete();
                    } else {
                        y.next(x.getFirst());
                    }
                    return x.stream().skip(1).toList();
                },
                (z) -> {
                    System.out.println("z: " + z);
                }
        ).subscribe(Util.subscriber());

    }


}
