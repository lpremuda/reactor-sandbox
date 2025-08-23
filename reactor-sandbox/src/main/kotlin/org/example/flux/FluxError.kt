package org.example.flux

import org.example.utils.subscribeStandard
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks

/**
 * https://projectreactor.io/docs/core/release/reference/coreFeatures/error-handling.html
 *
 * Keep in mind that any error in a reactive sequence is a terminal event. Even if an error-handling operator is used, it does not let the original sequence continue. Rather, it converts the onError signal into the start of a new sequence (the fallback one). In other words, it replaces the terminated sequence upstream of it
 *
 */

fun main() {
    Hooks.resetOnOperatorDebug()
//    fluxError()
//    fluxOnErrorResume()
//    fluxOnErrorReturn()
    fluxOnErrorComplete()
}

fun fluxError() {
    Flux.just(1, 2, 3)
        .map {
            if (it % 2 == 0) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .map { "num=$it" }
        .subscribeStandard()
}

fun fluxOnErrorResume() {
    Flux.just(1, 2, 3)
        .map {
            if (it % 2 == 0) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .onErrorResume { t -> Flux.just(4, 5, 6) }
        .map { "num=$it" }
        .subscribeStandard()
}

fun fluxOnErrorReturn() {
    Flux.just(1, 2, 3)
        .map {
            if (it % 2 == 0) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .onErrorReturn(10)
        .map { "num=$it" }
        .subscribeStandard()
}

fun fluxOnErrorComplete() {
    Flux.just(1, 2, 3)
        .map {
            if (it % 2 == 0) throw IllegalArgumentException("Odd numbers only!!!")
            it
        }
        .onErrorComplete()
        .map { "num=$it" }
        .subscribeStandard()
}
