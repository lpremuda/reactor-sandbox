package org.example.flux

import org.example.utils.log
import org.example.utils.subscribeStandard
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.Random

fun main() {
    Flux.just(1, 2, 3, 4, 5)
        .concatMap { t ->
            Flux.just(t)
                .doOnNext { log("concatMap: $it") }
                .delayElements(Duration.ofMillis(500))
                .subscribeOn(Schedulers.boundedElastic())
        }
        .doOnNext { log("doOnNext1: $it") }
        .collectList()
        .doOnNext { log("doOnNext2: $it") }
        .subscribeStandard()
}

private fun randomDelay(): Duration {
//    val delayMillis = ThreadLocalRandom.current().nextLong(100, 1000) // Random delay between 100ms and 1000ms
    val delayMillis = Random().nextLong(100, 1000)
    log("Generated delay: " + delayMillis + " ms")
    return Duration.ofMillis(delayMillis)
}
