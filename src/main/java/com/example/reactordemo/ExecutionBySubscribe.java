package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class ExecutionBySubscribe {

    public static void main(String[] args){

        log.info("begin...");
        Flux stringFlux = Flux.create(consumer -> {
            consumer.next("a");
            consumer.next("b");
            consumer.next("c");
        });
        log.info("after define string flux");

        Flux.range(1,10).subscribe(log::info);

        log.info("after int flux...");

        stringFlux.subscribe(s -> {
            log.info(Thread.currentThread() + " " + s);
        });

        log.info("end...");
    }
}
