package org.example

import reactor.core.publisher.Mono

class Main

fun main() {
    println("Hello World!")
    Mono.just(1).subscribe { println(it) }
}