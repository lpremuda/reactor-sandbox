package org.example.flux

import org.example.utils.log
import org.example.utils.subscribeStandard
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.concurrent.CountDownLatch

fun main() {
    val latch = CountDownLatch(1)

    Flux.just(1, 2, 3, 4)
        .doOnNext { log("doOnNext1: $it") }
        .publishOn(Schedulers.boundedElastic())
        .doOnNext { log("doOnNext2: $it") }
//        .delayElements(Duration.ofMillis(1))
        .doOnComplete { latch.countDown() }
        .subscribeStandard()

    latch.await()
}
