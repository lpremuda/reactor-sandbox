package org.example.flux

import org.example.utils.log
import reactor.core.publisher.Flux
import java.time.Duration

fun main() {
    log("Starting main")
    val lastVal: String? =
        Flux.just(1, 2, 3, 4)
            .doOnNext { log("doOnNext1: $it") }
            .delayElements(Duration.ofMillis(1000))
            .doOnNext { log("doOnNext2: $it") }
            .map { "num-$it" }
            .doOnNext { log("doOnNext3: $it") }
            .blockLast()

    lastVal?.let { log("Last value: $it") }

    log("Ending main")
}
