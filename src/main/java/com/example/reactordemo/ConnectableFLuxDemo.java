package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Instant;

@Log4j2
public class ConnectableFLuxDemo {

    public static void main(String[] args) {

        System.out.println("connectable flux...");
        ConnectableFlux connectableFlux = Flux.just("a,b,d,e".split(",")).publish();

        connectableFlux
                .subscribe(letter -> System.out.println(letter + "letter from " + Thread.currentThread() + " time:" + Instant.now()));

        connectableFlux
                .subscribe(letter -> System.out.println(letter + "letter from " + Thread.currentThread() + " time:" + Instant.now()));

        connectableFlux.connect();

        // 这个不会被执行，因为connect已经开始了。
        connectableFlux.subscribe(letter -> System.out.println("letter from main thread:" + letter + Thread.currentThread() + " time:" + Instant.now()));
    }
}
