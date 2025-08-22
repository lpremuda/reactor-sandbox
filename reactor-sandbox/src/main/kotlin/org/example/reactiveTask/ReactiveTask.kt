package org.example.reactiveTask

import mu.KotlinLogging
import org.example.logging.MDCFields
import org.example.logging.mdcWrite
import org.slf4j.MDC
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

object ReactiveTask {
    private const val TASK_STARTED = "Scheduled task started"
    private const val TASK_ENDED = "Scheduled task ended"
    private const val TASK_COMPLETED_SUCCESSFULLY = "Scheduled task completed successfully"
    private const val TASK_FAILED_WITH_EXCEPTION = "Scheduled task failed with exception"

    private val logger = KotlinLogging.logger {}
    private val startingMono: Mono<Void> = Mono.fromRunnable<Void> { logger.info(TASK_STARTED) }

    private val onComplete = { logger.info(TASK_COMPLETED_SUCCESSFULLY) }
    private val onFinally = {
        logger.info(TASK_ENDED)
        MDC.remove("tid")
        MDC.remove("task")
    }

    fun <T> Mono<T>.withStandardLogging(taskName: String): Mono<T> {
        val tid = UUID.randomUUID().toString()
        MDC.put("tid", tid)
        MDC.put("task", taskName)
        return startingMono.then(this)
            .doOnNext { _ -> onComplete() }
            .doOnError { throwable: Throwable -> logger.error(TASK_FAILED_WITH_EXCEPTION, throwable) }
            .doFinally { _ -> onFinally() }
            .mdcWrite(
                MDCFields(
                    tid = MDC.get("tid"),
                    task = MDC.get("task"),
                ),
            )
    }

    fun <T> Flux<T>.withStandardLogging(taskName: String): Flux<T> {
        val tid = UUID.randomUUID().toString()
        MDC.put("tid", tid)
        MDC.put("task", taskName)
        return startingMono.thenMany(this)
            .doOnComplete(onComplete)
            .doOnError { throwable: Throwable -> logger.error(TASK_FAILED_WITH_EXCEPTION, throwable) }
            .doFinally { _ -> onFinally }
            .mdcWrite(
                MDCFields(
                    tid = MDC.get("tid"),
                    task = MDC.get("task"),
                ),
            )
    }

//    fun <T> create(taskName: String, reactiveTask: Publisher<T>): Publisher<T> {
//        val tid = UUID.randomUUID().toString()
//        MDC.put("tid", tid)
//        MDC.put("task", taskName)
//        return when (reactiveTask) {
//            is Mono -> createMono(reactiveTask)
//            is Flux -> createFlux(reactiveTask)
//            else -> throw IllegalArgumentException("Unsupported reactive type: ${reactiveTask.javaClass.name}")
//        }
//    }
//
//    fun <T> createMono(monoTask: Mono<T>): Mono<T> {
//        return startingMono.then(monoTask)
//            .doOnNext { logger.info(TASK_COMPLETED_SUCCESSFULLY) }
//            .doOnError { throwable -> logger.error(TASK_FAILED_WITH_EXCEPTION, throwable) }
//            .doFinally {
//                logger.info(TASK_ENDED)
//                MDC.remove("tid")
//                MDC.remove("task")
//            }
//    }
//
//    fun <T> createFlux(fluxTask: Flux<T>): Flux<T> {
//        return startingMono.thenMany(fluxTask)
//            .doOnComplete { logger.info(TASK_COMPLETED_SUCCESSFULLY) }
//            .doOnError { throwable -> logger.error(TASK_FAILED_WITH_EXCEPTION, throwable) }
//            .doFinally {
//                logger.info(TASK_ENDED)
//                MDC.remove("tid")
//                MDC.remove("task")
//            }
//            .mdcWrite(
//                MDCFields(
//                    tid = MDC.get("tid"),
//                    task = MDC.get("task")
//                )
//            )
//    }
}
