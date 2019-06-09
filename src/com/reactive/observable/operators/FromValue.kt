package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observable.IObservable
import com.reactive.observers.IObserver

class FromValueObservable<T> : IObservable<T>{

    val value: T

    constructor(value: T){
       this.value = value
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        observer.onNext(this.value)
        observer.onComplete()
        return Disposable.empty()
    }

}