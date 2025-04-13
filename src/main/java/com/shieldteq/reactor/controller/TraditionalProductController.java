package com.shieldteq.reactor.controller;

import com.shieldteq.reactor.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/traditional")
public class TraditionalProductController {
    private final RestClient restClient;

    @GetMapping(value = "/products/notWorking", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsNotWorking() {
        List<Product> products = restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        log.info("Products : {}", products);
        return products;

    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Product>> getProducts() {
        return Mono.fromCallable(() -> restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {}))
                .subscribeOn(Schedulers.boundedElastic()) // offload blocking call
                .doOnNext(products -> log.info("Products: {}", products));
    }

}
