package com.shieldteq.reactor.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@RequiredArgsConstructor
public class SubscriptionImpl implements Subscription {
    private final Subscriber<? super String> subscriber;
    private static final int MAX_ITEMS = 10;
    private boolean cancelled;
    private int count;
    private static final Faker faker = new Faker();

    @Override
    public void request(long requested) {
        if (cancelled) return;
        log.debug("requested: {}", requested);
        log.debug("requested count: {}", requested);
        for (int i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(faker.name().firstName() + "@" + "mail.com");
        }

        if(count >= MAX_ITEMS) {
            log.debug("Reached max items: {}", requested);
            this.subscriber.onComplete();
            this.cancelled = true;
        }

    }

    @Override
    public void cancel() {
        log.debug("Subscriber cancelled");
        this.cancelled = true;
    }
}
