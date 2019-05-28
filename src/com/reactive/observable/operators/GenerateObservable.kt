package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception

class GenerateObservable<TSource, TResult>: IObservable<TResult>
{
    private val state: TSource
    private val condition: (TSource) -> Boolean
    private val iterate: (TSource) -> TSource
    private val selector: (TSource) -> TResult

    constructor(state: TSource,
                condition: (TSource) -> Boolean,
                iterate: (TSource) -> TSource,
                selector: (TSource) -> TResult)
    {
        this.state = state
        this.condition = condition
        this.iterate = iterate
        this.selector = selector
    }

    override fun subscribe(observer: IObserver<TResult>): Disposable
    {
        var newState = state
        while (condition(newState)){
            newState = iterate(newState)
            observer.onNext(selector(newState))
        }

        return Disposable.empty()
    }
}