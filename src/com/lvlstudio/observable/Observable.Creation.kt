package com.lvlstudio.observable

import com.lvlstudio.disposable.Disposable
import com.lvlstudio.obervers.IObserver
import com.lvlstudio.observable.operators.CreateObservable
import com.lvlstudio.observable.operators.DeferObservable
import com.lvlstudio.observable.operators.Generate

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