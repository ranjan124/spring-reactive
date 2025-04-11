package com.shieldteq.reactor.publisher;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class  PublisherImpl implements Publisher<String> {
    @Override
    public void subscribe(Subscriber<? super String> s) {
        Subscription subscription = new SubscriptionImpl(s);
        s.onSubscribe(subscription);
    }
}
