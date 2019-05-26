package com.lvlstudio.observable.operators

import com.lvlstudio.disposable.Disposable
import com.lvlstudio.obervers.IObserver
import com.lvlstudio.observable.IObservable
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