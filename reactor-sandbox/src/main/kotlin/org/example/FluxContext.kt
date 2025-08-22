package org.example

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Signal
import reactor.util.context.Context

fun main() {
    mdcLoggingExample()
}

fun basic() {
    Flux.just("x")
        .flatMap { v ->
            Mono.deferContextual {
                    ctx ->
                Mono.just(ctx.get<String>("k"))
            }
        }
        .contextWrite { Context.of("k", "downstream") }
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") },
        )
}

fun multipleInnerSubscriptions() {
    Flux.just(1, 2)
        .flatMap { i ->
            Mono.deferContextual { ctx ->
                log("k=${ctx.get<String>("k")}")
                Mono.just(i)
            }
        }
        .flatMap { i ->
            Mono.deferContextual { ctx -> Mono.just("$i:${ctx.get<String>("k")}") }
                .contextWrite { Context.of("k", "inner-$i") } // overrides for this inner
        }
        .contextWrite { Context.of("k", "outer") }
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") },
        )
}

fun mdcLoggingExample() {
    Flux.just("a", "b")
        .doOnEach { sig: Signal<String> ->
            if (sig.isOnNext) {
                val rid: String = sig.contextView.getOrDefault("requestId", "unknown") ?: "null"
                log("rid=$rid value=${sig.get()}")
            }
        }
        .contextWrite { Context.of("requestId", "123") }
        .subscribe(
            { value -> log("onNext: $value") },
            { error -> log("onError: $error") },
            { log("onComplete") },
        )
}
