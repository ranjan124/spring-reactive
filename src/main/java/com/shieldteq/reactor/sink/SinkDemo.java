package com.shieldteq.reactor.sink;

import com.shieldteq.reactor.demo.Util;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinkDemo {
    public static void main(String[] args) {
        demo3();
    }

    private static void demo1() {
        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();
        mono.subscribe(Util.subscriber());
        sink.tryEmitValue("hi");
    }

    private static void demo2() {
        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();
        mono.subscribe(Util.subscriber("pop"));
        mono.subscribe(Util.subscriber("cop"));
        sink.tryEmitValue("hi");
    }

    private static void demo3() {
        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();
        mono.subscribe(Util.subscriber("pop"));
        mono.subscribe(Util.subscriber("cop"));
        sink.emitValue("hi", (((signalType, emitResult) -> {
            log.info("HI");
            log.info("HI {}", signalType.name());
            log.info("HI {}", emitResult.name());
            return false;
        })));
        sink.emitValue("Hello", (((signalType, emitResult) -> {
            log.info("Hello");
            log.info("Hello {}", signalType.name());
            log.info("Hello {}", emitResult.name());
            return false;
        })));
    }
}
