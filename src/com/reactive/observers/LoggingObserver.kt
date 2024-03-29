package com.reactive.observers

import java.lang.Exception

class LoggingObserver<T>: IObserver<T> {

    override fun onComplete() {
        println("On Complete called")
    }

    override fun onError(exception: Exception) {
        println("On Error called with message ${exception.message}")
    }

    override fun onNext(value: T) {
        println("On Next called with value $value")
    }
}