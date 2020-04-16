package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class ObservableCache {

    public static void main(String[] args) {

        Flux stringFlux = Flux.create(consumer -> {
            System.out.println("begin creating string steam...");
            consumer.next("a");
            consumer.complete();
        }).cache();

        Flux intFlux = Flux.create(
                consumer -> {
                    System.out.println("begin creating int stream...");
                    consumer.next(1);
                    consumer.complete();
                }
        );

        stringFlux.subscribe(element -> {
            System.out.println("consume string firstly...");
            log.info(element);
        });

        intFlux.subscribe(element -> {
            System.out.println("consume int firstly...");
            log.info(element);
        });

        // 使用cache的strFlux，不用再创建
        stringFlux.subscribe(element -> {
            System.out.println("consume string secondly...");
            log.info(element);
        });

        // 这里会重新再创建一次intFlux
        intFlux.subscribe(element -> {
            System.out.println("consume int secondly...");
            log.info(element);
        });
    }
}
