package org.example.utils

import reactor.core.publisher.Flux
import java.util.concurrent.CountDownLatch

fun <T> withLatch(source: Flux<T>) {
    log("Starting withLatch")
    val latch = CountDownLatch(1)

    source
        .doOnComplete { latch.countDown() }
        .subscribeStandard()

    latch.await()
    log("Ending withLatch")
}
