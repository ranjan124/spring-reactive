package com.shieldteq.reactor.subscriber;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Getter
@Slf4j
public class EmailSubscriber implements Subscriber<String> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
    }

    @Override
    public void onNext(String s) {
        log.info("Message received {}", s);
    }

    @Override
    public void onError(Throwable t) {
        log.error("Error occurred {}", t.toString());
    }

    @Override
    public void onComplete() {
        log.info("Completed");
    }
}
