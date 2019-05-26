package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception

class CreateObservable<T> : IObservable<T> {

    private val _subscribe: (IObserver<T>) -> Disposable

    constructor(subscribe: (IObserver<T>) -> Disposable){
        _subscribe = subscribe
    }

    override fun subscribe(observer: IObserver<T>) : Disposable {
        val create = CreateObserver(observer)
        return _subscribe(create)
    }

    inner class CreateObserver<T>: OperatorObserverBase<T, T>
    {
        constructor(parent: IObserver<T>): super(parent)

        override fun OnNext(value: T) {
            super.observer.OnNext(value)
        }

        override fun onComplete() {
           try {
                observer.onComplete()
           }finally {
               dispose()
           }
        }

        override fun onError(exception: Exception) {
            try {
                observer.onError(exception)
            }finally {
                dispose()
            }
        }

    }
}