package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observable.IObservable
import com.reactive.observers.IObserver
import java.lang.Exception

class TakeObservable<T> : IObservable<T> {

    val source: IObservable<T>
    val limit: Int

   constructor(source: IObservable<T>, limit: Int){
       this.source = source
       this.limit = limit
   }

    override fun subscribe(observer: IObserver<T>): Disposable {
        val takeObserver = TakeObserver(this, observer)
        return source.subscribe(takeObserver)
    }

    inner class TakeObserver<T> : OperatorObserverBase<T, T>
    {
        val limit: Int
        var iteration: Int = 0

        constructor(parent: TakeObservable<T>, observer: IObserver<T>): super(observer){
            this.limit = parent.limit
        }

        override fun onNext(value: T) {

            if(iteration < limit){
                iteration++
                observer.onNext(value)
            }
            else
                observer.onComplete()

        }

        override fun onComplete() {
            try { observer.onComplete()} finally { dispose() }
        }

        override fun onError(exception: Exception) {
            try { observer.onError(exception)} finally { dispose() }
        }
    }
}