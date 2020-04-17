package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
public class FluxContextDemo {

    public static void main(String[] args) {

        String key = "message";

        // 在使用context里面的内容时候，要使用flatMap，如果使用map的话，放回的类型就是MonoMapFuseable
        Flux.range(1,1)
                .flatMap(s -> Mono.subscriberContext().map(ctx -> s + " " + ctx.get(key)))
                //
                //.Map(s1 -> Mono.subscriberContext().map(ctx -> s1  + ctx.get("test1")))
                .map(s -> s.trim())
                .subscriberContext(ctx -> ctx.put(key, "World"))
                .subscriberContext(ctx -> ctx.put("test1", "World1"))
                .subscribe(s -> log.info(s));
    }

}
