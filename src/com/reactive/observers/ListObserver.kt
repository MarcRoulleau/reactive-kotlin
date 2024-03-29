package com.reactive.observers

import java.lang.Exception
import kotlin.collections.ArrayList

class ListObserver<T> : IObserver<T> {

    val observers : ArrayList<IObserver<T>> = ArrayList()

    constructor(observer: IObserver<T>){
        observers.add(observer)
    }

    fun Add(observer: IObserver<T>): List<Any> {
        return listOf(observers, observer)
    }

    override fun onNext(value: T) {
        for (obs in observers){
            obs.onNext(value)
        }
    }

    override fun onComplete() {
        for (obs in observers){
            obs.onComplete()
        }
    }

    override fun onError(exception: Exception) {
       for (obs in observers){
           obs.onError(exception)
       }
    }
}