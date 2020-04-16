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
public class SubscribeOnFLuxDemo {

    public static void main(String[] args) {


        ExecutorService poolA = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-a"));
        ExecutorService poolB = Executors.newFixedThreadPool(3, new CustomizableThreadFactory("thread-factory-b"));

        // subscribeOn 决定 Observable.create 的执行线程，之后再写 subscribeOn ，无论是挨着写，还是隔着操作符写都没有作用
        // 每每onNext那个元素的操作所在的线程
        // subscribeOn 决定数据源的执行线程后，也会当前线程置为这个线程，若无其他设置，之后操作符的操作也是在当前线程执行，也就是 subscribeOn 指定的线程
        // 在这种cold subscribable 给定了线程池也不一定切换，除非有delay等操作
        Flux.just("a,b,d,e".split(","))
        // 如果不使用delay元素的话，subscribeOn的线程也不切换（如果不配置scheduler，如果直接delay的话，程序就终止了）
                .delayElements(Duration.ofMillis(1000))
                .map(letter -> letter.toUpperCase())
                .filter(letter -> {
                    log.info(letter);
                    return true;
                })
                .subscribeOn(Schedulers.fromExecutorService(poolA))
                .map(letter -> letter + letter)
                .filter(letter -> {
                    log.warn(letter);
                    return true;
                })
                .subscribeOn(Schedulers.fromExecutorService(poolB))
                .map(letter -> Mono.just(letter).repeat(3))
                .filter(letter -> {
                    log.error(letter.subscribe(log::error));
                    return true;
                })
                .subscribe(letter -> log.fatal(letter.subscribe((log::fatal))));
    }
}
