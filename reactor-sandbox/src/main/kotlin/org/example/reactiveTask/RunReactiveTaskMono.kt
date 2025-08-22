package org.example.reactiveTask

import mu.KotlinLogging
import org.example.reactiveTask.ReactiveTask.withStandardLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

private val logger = KotlinLogging.logger {}

fun main () {
    val source = Mono.just("Hello")
        .doOnNext { logger.info("doOnNext: $it") }

    val sourceError = Mono.error<String>(RuntimeException("Kaboom!"))
        .doOnNext { logger.info("doOnNext: $it") }

    sourceError
        .withStandardLogging("myTask")
        .subscribe()
}
