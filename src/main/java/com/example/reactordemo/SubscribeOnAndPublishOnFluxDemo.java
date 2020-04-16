package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class SubscribeOnAndPublishOnFluxDemo {

    public static void main(String[] args) {

        ExecutorService poolC = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-subC"));
        ExecutorService poolD = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-pushD"));
        ExecutorService poolE = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-pushE"));

        Flux.just("a,b,c,d,e".split(","))
                .delayElements(Duration.ofMillis(1000))
                .map(letter -> letter.toUpperCase())
                .filter(letter -> {
                    log.info(letter +  "before publishOn/subscribeOn ==>" + Thread.currentThread());
                    return true;
                })
                //.publishOn(Schedulers.fromExecutorService(poolD))
                .subscribeOn(Schedulers.fromExecutorService(poolC))
                .map(letter -> letter + letter)
                .filter(letter -> {
                    log.warn(letter + "after first publishOn/subscribeOn ==>" + Thread.currentThread());
                    return true;
                })
                //.publishOn(Schedulers.fromExecutorService(poolE))
                .map(letter -> Mono.just(letter).repeat(3))
                .filter(letter -> {
                    log.error(letter.subscribe(letterRep -> log.error(letterRep + "after second publishOn ==>" + Thread.currentThread())));
                    return true;
                })
                .subscribe(letter -> log.fatal(letter.subscribe((log::fatal))));
    }
}
