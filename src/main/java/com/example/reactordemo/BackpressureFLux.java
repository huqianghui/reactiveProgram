package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Log4j2
public class BackpressureFLux {

    public static void main(String[] args) {
        Flux.range(1, 105)
                .onBackpressureBuffer(100, BufferOverflowStrategy.DROP_LATEST)
                .subscribeOn(Schedulers.newSingle("backpressure"))
                .subscribe(i -> {
                    log.info(i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

}
