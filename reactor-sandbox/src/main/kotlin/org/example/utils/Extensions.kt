package org.example.utils

import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.function.Consumer

fun <T> logOnNext(): Consumer<T> = Consumer { t -> log("onNext: $t") }

private fun logOnError(): Consumer<Throwable> = Consumer { e -> log("onError: $e") }

private fun logOnComplete(): Runnable = Runnable { log("onComplete") }

fun <T> Mono<T>.subscribeStandard(): Disposable {
    return this.subscribe(
        logOnNext(),
        logOnError(),
        logOnComplete(),
    )
}

fun <T> Flux<T>.subscribeStandard(): Disposable {
    return this.subscribe(
        logOnNext(),
        logOnError(),
        logOnComplete(),
    )
}
