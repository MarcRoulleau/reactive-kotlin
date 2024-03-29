package com.reactive.observers

import java.lang.Exception

class EmptyObserver<T> : IObserver<T> {

    companion object {
        fun <T> instance() : EmptyObserver<T> {
           return EmptyObserver()
        }
    }

    override fun onNext(value: T) { }

    override fun onComplete() { }

    override fun onError(exception: Exception) { }
}