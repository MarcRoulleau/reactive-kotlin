package com.lvlstudio.observable.operators

import com.lvlstudio.disposable.Disposable
import com.lvlstudio.obervers.IObserver
import com.lvlstudio.observable.IObservable

class Generate<TSource, TResult>: IObservable<TResult>
{
    private var state: TSource
    private var condition: (TSource) -> Boolean
    private var iterate: (TSource) -> TSource
    private var selector: (TSource) -> TResult

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
            observer.OnNext(selector(newState))
        }

        return Disposable.empty()
    }
}