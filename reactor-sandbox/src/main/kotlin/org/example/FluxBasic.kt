package org.example

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun main() {
//    Hooks.onOperatorDebug()
    Flux.just("Lucas", "Michael", "Allen") // FluxArray<>(array)
        .doOnError { println("onError1: $it") } // FluxPeek<>(FluxArray<>(array), onError)
        .checkpoint("after-doOnError1")
        .map { it.uppercase() } // FluxMap<>(FluxPeek<>(FluxArray<>(array), onError), mapper)
        .map {
            if (it == "ALLEN") throw IllegalArgumentException("Allen is not allowed")
            it
        } // FluxMap<>(FluxMap<>(FluxPeek<>(FluxArray<>(array), onError), mapper), mapper)
        .flatMap { Mono.just(it) }
        .doOnError {
            println("onError2: $it")
        } // FluxPeek<>(FluxMap<>(FluxMap<>(FluxPeek<>(FluxArray<>(array), onError), mapper), mapper), onError)
        // "source"
        .subscribe(
            { value -> println("onNext: $value") },
            { error -> println("onError: $error") },
            { println("onComplete") },
        )
    // subscribe(consumer, errorConsumer, completeConsumer)
    // subscribeWith(new LambdaSubscriber<>(consumer, errorConsumer, completeConsumer, null, initialContext))
    // subscribe(subscriber); return subscriber;
    //
    // source.subscribe(subscriber);

    /**
     * 	public void subscribe(CoreSubscriber<? super T> actual) {
     * 		subscribe(actual, array);
     * 	}
     *
     * 	s.onSubscribe(new ArraySubscription<>(s, array));
     *
     *
     *
     */

    /**
     *
     *
     *
     */
}
