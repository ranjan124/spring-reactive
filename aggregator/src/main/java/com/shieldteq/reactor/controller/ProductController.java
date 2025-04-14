package com.shieldteq.reactor.controller;

import com.shieldteq.reactor.dto.Product;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final Faker faker;

    @GetMapping
    public Flux<Product> getProducts() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(10))
                .map(x -> Product.builder().id(faker.idNumber().peselNumber()).name(faker.name().fullName()).build())
                .onErrorComplete();
    }
}
