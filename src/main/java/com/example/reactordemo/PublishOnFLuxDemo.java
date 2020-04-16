package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class PublishOnFLuxDemo {

    public static void main(String[] args) {


        ExecutorService poolC = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-C"));
        ExecutorService poolD = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-D"));

        // publishOn 更能够更改 publishOn 之后书写的操作符的执行线程，也就是可以切换当前线程
        Flux.just("a,b,d,e".split(","))
                .delayElements(Duration.ofMillis(1000))
                .map(letter -> letter.toUpperCase())
                .filter( letter -> {log.info(letter + "    **    ");return true;})
                .publishOn(Schedulers.fromExecutorService(poolC))
                .map(letter -> letter + letter)
                .filter(letter -> {log.warn(letter + "    %%    ");return true;})
                .publishOn(Schedulers.fromExecutorService(poolD))
                .map(letter ->  Mono.just(letter).repeat(3))
                .filter(letter -> {log.error( letter.subscribe(log::error) + "    $$    ");return true;})
                .subscribe(letter -> log.fatal(letter.subscribe(log::fatal) + "    ##    "));
    }
}
