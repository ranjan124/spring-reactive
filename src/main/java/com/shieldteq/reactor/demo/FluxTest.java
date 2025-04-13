package com.shieldteq.reactor.demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxTest {
    public static void main(String[] args) {
//        testRange();
        generateName();
//        generateName1();
    }

    private static void generateName() {
        SubscriberImpl subscriber = new SubscriberImpl();
        NameGenerator.getNamesFlux(10)
                .subscribe(subscriber);
        subscriber.getSubscription().request(3);
        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);

    }

    private static void generateName1() {
//        SubscriberImpl subscriber = new SubscriberImpl();
        System.out.println(NameGenerator.getNamesList(10));
//        subscriber.getSubscription().request(3);

    }

    private static void testRange() {
        Flux.range(1, 5)
                .log()
                .subscribe(Util.subscriber());
    }
}
