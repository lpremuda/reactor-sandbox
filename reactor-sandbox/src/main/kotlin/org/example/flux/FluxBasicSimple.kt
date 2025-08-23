package org.example.flux

import org.example.utils.subscribeStandard
import reactor.core.publisher.Flux
import reactor.core.publisher.Hooks
import reactor.core.publisher.Mono

fun main() {
    Hooks.resetOnOperatorDebug()
//    mapFlatmap()
    doOnNextMap()
}

fun fluxSource() = Flux.just("Chloe", "Jessica", "Gene") // FluxArray<>(array)

fun mapFlatmap() {
    fluxSource()
        .map { it.uppercase() } // FluxMap<>((FluxArray<>(array), onError), mapper))
        .flatMap { Mono.just(it + "-END") }
        .subscribeStandard()
}

fun doOnNextMap() {
    fluxSource()
        .doOnComplete { println("doOnComplete1") }
        .doOnNext { t -> println("doOnNext1: $t") }
        .doOnComplete { println("doOnComplete2") }
        .doOnNext { t -> println("doOnNext2: $t") }
        .map { it.uppercase() }
        .doOnComplete { println("doOnComplete3") }
        .subscribeStandard()
}
