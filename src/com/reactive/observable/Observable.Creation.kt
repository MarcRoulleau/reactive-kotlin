package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.obervers.IObserver
import com.reactive.observable.operators.CreateObservable
import com.reactive.observable.operators.DeferObservable
import com.reactive.observable.operators.Generate

class Observable {
    companion object {
        fun <T> create(subscribe: (IObserver<T>) -> Disposable) : IObservable<T> {
            return CreateObservable(subscribe)
        }

        fun <TSource, TResult> generate(state: TSource, condition: (TSource) -> Boolean,
                                        iterate: (TSource) -> TSource,
                                        selector: (TSource) -> TResult): IObservable<TResult>
        {
            return Generate(state, condition, iterate, selector)
        }

        fun <T> defer(subscribe: () -> IObservable<T> ): IObservable<T>{
            return DeferObservable(subscribe)
        }

    }
}