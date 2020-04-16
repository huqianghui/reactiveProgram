package com.example.reactordemo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@SpringBootApplication
@Log4j2
@EnableWebFlux
@EnableReactiveMongoRepositories
public class ReactorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactorDemoApplication.class, args);
    }
}

@RestController
@Log4j2
class GreetingController {

    private final  UserRepository userRepository;

    @Autowired
    public GreetingController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/greeting/{name}")
    public Flux<User> greetToSomebody(@PathVariable("name") String name) {
        Assert.isTrue(!StringUtils.isEmpty(name), "the parameter is null.");
        return userRepository.findByName(name);
    }


    @GetMapping("/greeting2/{name}")
    public Mono<String> greet2ToSomebody(@PathVariable("name") String name) {
        Assert.isTrue(!StringUtils.isEmpty(name), "the parameter is null.");
        return Mono.just(name).doOnNext(log::info).map(actualName -> "hello " + actualName);
    }

}