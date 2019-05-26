package com.reactive.obervers

import java.lang.Exception

interface IObserver<T> {
    fun OnNext(value: T)
    fun onComplete()
    fun onError(exception: Exception)
}