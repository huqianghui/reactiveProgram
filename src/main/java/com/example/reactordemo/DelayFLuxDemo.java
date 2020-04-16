package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Log4j2
public class DelayFLuxDemo {

    public static void main(String[] args){

        log.info("begin program...");
        // 在当前线程中delay就没有办法执行
        // Flux.range(1,10).delayElements(Duration.ofMillis(1000), Schedulers.single()).subscribe(i -> System.out.println(i));
        Flux.range(1,10).delayElements(Duration.ofMillis(1000), Schedulers.newSingle("sleepUp")).subscribe(i -> log.error(i));
        //Flux.interval(Duration.ofMillis(1000)).subscribe(i -> log.info(i));
        log.info("end program...");
    }
}
