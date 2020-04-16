package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Log4j2
public class UnsubscribeStream {
    public static void main(String[] args) throws InterruptedException {

        // 有限的流程，或者通过元素和时间判断来终止 takeUntil
        Flux.just("a,b,c".split(","))
                .takeUntil(s -> s.equalsIgnoreCase("b"))
                .subscribe(log::info);

        // 通过disposal来终止，这个时间点在并发或者并行的时候，线程通过判断cancel状态来判断
        // 在subscription线程来终止订阅，而另外的消费线程得到通知后，停止
        Disposable test = (Disposable) Flux.create(consumer -> {
            Thread worker = new Thread(() -> {
                int i = 0;
                while (!consumer.isCancelled()) {
                    consumer.next(i++);
                }
            });
            worker.start();
        }).subscribe(log::info);

        log.info("worker thread begin...");
        log.info("main thread begin sleep 1 second...");
        Thread.sleep(10);
        test.dispose();
        log.info("stop subscription...");
    }
}
