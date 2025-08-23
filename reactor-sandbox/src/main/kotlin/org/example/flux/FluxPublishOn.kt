package org.example.flux

import org.example.utils.log
import org.example.utils.withLatch
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks
import reactor.core.scheduler.Schedulers

fun main() {
    Hooks.resetOnOperatorDebug()
    val source =
        Flux.just(1, 2, 3, 4)
//            .doOnNext { log("doOnNext1: $it") }
            .publishOn(Schedulers.boundedElastic())
            .doOnNext { log("doOnNext2: $it") }

    withLatch(source)
}
