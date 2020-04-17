package com.example.reactordemo.test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ReactorTest {

    public static void main(String[] args) {

        // 通过断言java.lang.AssertionError的方式验证数据，生命周期事件等
        // 如果验证通过，则正常退出，否则抛出AssertionError
        StepVerifier.create(Flux.just(("a,b,c")
                .split(",")))
                .expectNext("a")
                .expectNext("b")
                .expectNext("c")
                .expectComplete()
                .verify();

        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1))
                .take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .expectComplete()
                .verify();
    }
}
