package org.example

import reactor.core.publisher.Flux
import java.time.Duration

fun main() {
    Flux.just(1, 2, 3, 4)
        .delayElements(Duration.ofMillis(500))
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") },
        )
}
