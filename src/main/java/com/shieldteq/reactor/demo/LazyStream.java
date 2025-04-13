package com.shieldteq.reactor.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class LazyStream {
    public static void main(String[] args) {
        Stream.of(1).peek(x -> log.info("value: {}", x)).toList();
    }
}
