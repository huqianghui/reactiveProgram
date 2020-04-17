package com.example.reactordemo.debug;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class ReactorCheckPoint {

    public static void main(String[] args){

        // 通过checkpoint来判断 异常点发生在哪里
        // 然后在异常点之前，通过log的方法，输出数据等，来进一步排查错误
        Flux.range(1, 3)
                .map(i -> i + " ")
                .checkpoint("map1")
                .log()
                .filter(str -> {
                    if (str.startsWith("3")) {
                        throw new RuntimeException();
                    } else {
                        return true;
                    }
                })
                .checkpoint("filter1")
                .map(str -> str.trim())
                .checkpoint("map2")
                .subscribe(log::info);
    }
}
