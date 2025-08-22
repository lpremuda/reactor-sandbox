package org.example

import org.example.extensions.subscribeStandard
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks

fun main() {
    Hooks.resetOnOperatorDebug()
    Flux.just(1, 2, 3)
        .map {
            if (it % 2 == 0) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .map { "num=$it" }
        .subscribeStandard()
}
