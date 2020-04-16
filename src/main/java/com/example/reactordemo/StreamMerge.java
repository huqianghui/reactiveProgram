package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class StreamMerge {

    public static void main(String[] args){

        Flux<String> firstFlux = Flux.create( s -> {
            new Thread(() -> {
                s.next("a");
                s.next("b");
                s.next("c");
                s.next("d");
                s.next("e");
                s.complete();
            }).start();
        });

        Flux<String> secondFlux = Flux.create(s->{
            new Thread(() -> {
                s.next("f");
                s.next("g");
                s.next("h");
                s.next("i");
                s.next("j");
                s.complete();
            }).start();
        });

        Flux.merge(firstFlux,secondFlux).subscribe(log::info);


    }
}
