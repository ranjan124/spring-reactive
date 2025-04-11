package com.shieldteq.reactor.demo;

import com.shieldteq.reactor.publisher.PublisherImpl;
import com.shieldteq.reactor.subscriber.EmailSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        demo1();
        demo2();
        demo3();
    }

    private static void demo1() {
        log.info("demo1");
        PublisherImpl publisher = new PublisherImpl();
        EmailSubscriber subscriber = new EmailSubscriber();
        publisher.subscribe(subscriber);

    }
    private static void demo2() throws InterruptedException {
        log.info("demo2");
        PublisherImpl publisher = new PublisherImpl();
        EmailSubscriber subscriber = new EmailSubscriber();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);

    }
    private static void demo3() throws InterruptedException {
        log.info("demo3");
        PublisherImpl publisher = new PublisherImpl();
        EmailSubscriber subscriber = new EmailSubscriber();
        publisher.subscribe(subscriber);

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(3);

    }
}
