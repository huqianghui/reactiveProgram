package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Random;

@Log4j2
public class FLuxGenerate {

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
            // next()方法只能最多被调用一次
            synchronousSink.next("1");
            // synchronousSink.next("2");
            synchronousSink.complete();
        }).subscribe(log::info);


        final Random  intRandom = new Random();

        // 产生的过程中需要一些状态维护，本地中通过一个ArrayList的实例维护这个状态
        // 这样在后面这个BiFunction里面可以使用这个list作为参数传入
        // 同时在还BiFunction中作为条件判断，结束条件等。

        // 第二个参数调用的，第一个参数的创建一个初始值ArrayList
        Flux.generate(ArrayList::new, (list,sink) -> {
                int value = intRandom.nextInt(100);
                list.add(value + " hello");
                sink.next(value);
                // 通过状态判断，结束数据流，否则会一次次调用这个方法，把这个list给回第一个参数
                // 然后第一个参数传递给第二个函数的第一个参数，来执行第二个方法
                if(list.size() == 10){
                    sink.complete();
                }
                return list;
        }).subscribe(log::info);
    }

}
