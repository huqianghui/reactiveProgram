# Reactive Program

之前接触过akka，然后也使用过spark，同时因为在spring5中使用大量的reactive 编程，在hystrix等模块中的实现。

导致很多代码看起来不好理解和debug。

最近因为要通过graphQL做数据网关，需要继承很多服务，所以需要考虑并发数量。

这样选择reactive program 也就是 spring WebFlux + reactive mongoDB等

同时reactvie program 约等于 异步程序 + 背压等功能。

综上，响应式编程中的事件驱动，要求：

1.对事件建模

2.对事件流程建模

3.对事件相关性建模
