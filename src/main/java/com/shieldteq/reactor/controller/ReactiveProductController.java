package com.shieldteq.reactor.controller;

import com.shieldteq.reactor.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive")
public class ReactiveProductController {
    private final WebClient webClient;

    @GetMapping(value = "/products")
    public Flux<Product> getProducts() {
        return webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(product -> log.info("Products : {}", product));
    }

    @GetMapping(value = "/products/notorious")
    public Flux<Product> getProductsNotorious() {
        return webClient.get()
                .uri("/demo01/products/notorious")
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(product -> log.info("Products notorious: {}", product));
    }

}
