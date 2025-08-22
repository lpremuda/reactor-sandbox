package org.example.logging

import net.logstash.logback.marker.LogstashMarker
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.context.Context
import kotlin.reflect.full.memberProperties

fun log(message: String) {
    val threadName: String = Thread.currentThread().name
    println("[$threadName] $message")
}

data class MDCFields(
    val task: String? = null,
    val tid: String? = null,
) {
    fun asMap(): MutableMap<String, Any> {
        val props = MDCFields::class.memberProperties.associateBy { it.name }
        val map = mutableMapOf<String, Any>()
        props.keys.forEach {
            val value = props[it]?.get(this)
            if (value != null) map[it] = value
        }
        return map
    }

    fun asMarkers(): LogstashMarker {
        return markerMap(this.asMap())
    }
}

fun <T> Mono<T>.mdcWrite(fields: MDCFields): Mono<T> {
    return this.contextWrite(Context.of(fields.asMap()))
}

fun <T> Flux<T>.mdcWrite(fields: MDCFields): Flux<T> {
    return this.contextWrite(Context.of(fields.asMap()))
}
