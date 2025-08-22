package org.example

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom
import java.util.Random


fun main() {
    Flux.just(1, 2, 3, 4)
        .delayElements(Duration.ofMillis(500))
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") }
        )
}
