package org.example.mono

import reactor.core.publisher.Mono

fun main() {
    Mono.just("Hello World!")
        .map { it.uppercase() }
        .subscribe(
            { value -> println("onNext: $value") },
            { error -> println("onError: $error") },
            { println("onComplete") },
        )
}
