package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class FluxCreateTest {

    public static void main(String[] args){

        // 通过fluxSink来包装一个数据源，知道调用complete 或者error 为止
        Flux.create(s -> {
            s.next("one");
            s.next("two");
            s.next("three");
            s.complete();}).subscribe(log::info);
    }
}
