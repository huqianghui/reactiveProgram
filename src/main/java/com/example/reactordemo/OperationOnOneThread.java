package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Log4j2
public class OperationOnOneThread {

    public static void main(String[] args){
        Flux.just("a").subscribe(log::info);

        // 1) 证明map filter的操作总是同步执行在发送数据的线程上
        // 设置subscribeOn daemon（subscribeOn(Schedulers.newSingle("sub"，true))）看不到结果。不要设置daemon属性！！
        Flux.just("a,b,c".split(","))
                .publishOn(Schedulers.newSingle("publish",true))
                .doOnNext( str -> System.out.println("str:" + str + " " + Thread.currentThread()))
                .filter( str -> {
                                System.out.println("str:" + str + " " + Thread.currentThread());
                                return str.equalsIgnoreCase("a") || str.equalsIgnoreCase("b");
                                })
                .map(str -> {
                        System.out.println("str:" + str + " " + Thread.currentThread());
                        return str.toUpperCase();
                        })
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(str -> log.info("str:" + str + " " + Thread.currentThread()));
    }
}
