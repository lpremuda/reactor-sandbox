package org.example

import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono

fun main() {
    Hooks.resetOnOperatorDebug()
    Flux.just(1, 2, 3)
        .map {
            if (it == 2) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") }
        )
}

