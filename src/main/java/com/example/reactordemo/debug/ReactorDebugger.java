package com.example.reactordemo.debug;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

@Log4j2
public class ReactorDebugger {

    public static void main(String[] args) {
        // log()操作符，能够记录其上游的Flux或 Mono的事件(包括onNext、onError、onComplete， 以及onSubscribe、cancel、和request）)
        Flux.range(1, 2).log().subscribe(log::info);

        Hooks.onOperatorDebug();
        Flux.range(1, 3)
                .map(i -> i + " ")
                .filter(str -> {
                    if (str.startsWith("3")) {
                        throw new RuntimeException();
                    } else {
                        return true;
                    }
                })
                .subscribe(log::info);

    }
}
