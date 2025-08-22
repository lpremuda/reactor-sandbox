package org.example

import org.example.extensions.subscribeStandard
import reactor.core.publisher.Flux
import java.time.Duration

fun main() {
    Flux.just(1, 2, 3, 4)
        .delayElements(Duration.ofMillis(500))
        .subscribeStandard()
}
