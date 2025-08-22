package org.example

fun log(message: String) {
    val threadName: String = Thread.currentThread().name
    println("[$threadName] $message")
}