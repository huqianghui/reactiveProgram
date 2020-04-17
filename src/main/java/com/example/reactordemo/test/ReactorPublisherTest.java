package com.example.reactordemo.test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class ReactorPublisherTest {

    public static void main(String[] args) {

        // TestPublisher 主要用来测试开发人员自己创建的操作符
        TestPublisher testPublisher = TestPublisher.create();
        UppercaseConverter uppercaseConverter = new UppercaseConverter(testPublisher.flux());

        StepVerifier.create(uppercaseConverter.getUpperCase())
                .then(() -> testPublisher.emit("aA", "bb", "ccc"))
                .expectNext("AA", "BB", "CCC")
                .verifyComplete();
    }

   static class UppercaseConverter {
        private final Flux<String> source;

        UppercaseConverter(Flux<String> source) {
            this.source = source;
        }

        Flux<String> getUpperCase() {
            return source
                    .map(String::toUpperCase);
        }
    }
}
