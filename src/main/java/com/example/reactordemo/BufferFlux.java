package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Log4j2
public class BufferFlux {

    public static void main(String[] args){

        // buffer会缓存数据，当缓存的数据的数量达到设置的时或者时间窗口结束时，才批量返回数据
        // 看每个的时间可以和window区分出来
        Flux.range(1,30)
                .buffer(3)
                .subscribe((List<Integer> buffed) -> log.info(buffed));
    }
}
