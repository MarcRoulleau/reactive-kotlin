package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.obervers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception

class DeferObservable<T>: IObservable<T> {

    val _observableFactory: () -> IObservable<T>

    constructor(observableFactory: () -> IObservable<T>){
        _observableFactory = observableFactory
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        var observable = _observableFactory()

        return observable.subscribe(Defer(observer))
    }

    inner class Defer<T>: OperatorObserverBase<T, T>{

        val _observer: IObserver<T>

        constructor(observer: IObserver<T>): super(observer){
            _observer = observer
        }

        override fun OnNext(value: T) {

            try {
                _observer.OnNext(value)
            }
            catch (exception: Exception){
                _observer.onError(exception)
            }
            finally {
                dispose()
            }
        }

        override fun onComplete() {
            try {
                _observer.onComplete()
            } finally {
               dispose()
            }
        }

        override fun onError(exception: Exception) {
            try {
                _observer.onError(exception)
            }finally {
               dispose()
            }
        }

    }

}