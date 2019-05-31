package com.reactive.observers

import java.lang.Exception
import java.util.concurrent.atomic.AtomicInteger

abstract class ObserverBase<T> : IObserver<T> {

    var isStopped = AtomicInteger(0)

    override fun onNext(value: T) {

        if(isStopped.get() == 0){
            onNextCore(value)
        }
    }

    override fun onComplete() {

        if(isStopped.getAndSet(1) == 0){
            onCompleteCore()
        }
    }

    override fun onError(exception: Exception) {

        if(isStopped.getAndSet(1) == 0){
            onErrorCore(exception)
        }
    }

    abstract fun onNextCore(value: T)
    abstract fun onCompleteCore()
    abstract fun onErrorCore(exception: Exception)

}