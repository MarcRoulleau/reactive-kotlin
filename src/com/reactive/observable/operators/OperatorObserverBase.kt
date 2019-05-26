package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observers.EmptyObserver
import com.reactive.observers.IObserver
import java.lang.Exception

abstract class OperatorObserverBase<TSource, TResult>: IObserver<TSource>, Disposable {

    var observer: IObserver<TResult> // TODO make observer thread safe
    private var _isDiposed = false

    constructor(observer: IObserver<TResult>)
    {
        this.observer = observer
    }

    abstract override fun OnNext(value: TSource)

    abstract override fun onComplete()

    abstract override fun onError(exception: Exception)

    override fun dispose() {
        //TODO make it thread safe
        observer = EmptyObserver.instance()
    }
}