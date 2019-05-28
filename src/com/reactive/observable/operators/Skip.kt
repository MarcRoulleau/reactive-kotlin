package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observable.IObservable
import com.reactive.observers.IObserver
import java.lang.Exception

class SkipObservable<T> : IObservable<T> {

    val source : IObservable<T>
    val count: Int

    constructor(source : IObservable<T>, skip: Int){
        this.source = source
        this.count = skip
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        return source.subscribe(SkipObserver(observer, this))
    }

    inner class SkipObserver<T> : OperatorObserverBase<T, T>{

        var remaining: Int

        constructor(observer: IObserver<T>, parent: SkipObservable<T>) : super(observer){
            this.remaining = parent.count
        }

        override fun onNext(value: T)
        {
            if(remaining <= 0)
            {
                observer.onNext(value)
            }
            else
            {
                remaining--
            }
        }

        override fun onComplete() {
            try {
                observer.onComplete()
            }
            finally {
                dispose()
            }
        }

        override fun onError(exception: Exception) {
            try {
                observer.onError(exception)
            }
            finally {
                dispose()
            }
        }
    }

}