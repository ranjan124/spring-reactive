package com.shieldteq.reactor.demo;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class FluxNameGenerator implements Consumer<FluxSink<String>> {
    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> stringFlux) {
        this.fluxSink = stringFlux;
    }

    public void generate() {
        this.fluxSink.next(Util.faker().name().fullName());
    }
}
