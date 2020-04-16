package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
public class WindowFlux {

    public static void main(String[] args){
        // window则是在Publisher发布数据之后，立刻返回数据，直到返回的数据的数量达到设置的值，或者时间窗结束
        // 看每个的时间可以和buffer区分出来
        Flux.range(1,30).window(10).subscribe(windowFlux -> windowFlux.subscribe(log::info));
    }
}

