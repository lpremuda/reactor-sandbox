package org.example.reactiveTask

import mu.KotlinLogging
import org.example.reactiveTask.ReactiveTask.withStandardLogging
import reactor.core.publisher.Flux
import java.time.Duration

private val logger = KotlinLogging.logger {}

fun main() {
    val source =
        Flux.just(1, 2, 3, 4)
            .doOnNext { logger.info("doOnNext: $it") }

    source
        .withStandardLogging("myTask")
        .delayElements(Duration.ofSeconds(1))
        .subscribe()

    source
        .withStandardLogging("myTask2")
        .subscribe()
}
