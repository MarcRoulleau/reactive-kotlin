package com.reactive.observable

import com.reactive.observable.operators.SelectManyObservable
import com.reactive.observable.operators.SelectObservable

fun <TSource, TResult> IObservable<TSource>.select(selector: (TSource) -> TResult): IObservable<TResult>{
    return SelectObservable(this, selector)
}

fun <TSource, TResult> IObservable<TSource>.selectMany(selector: (TSource)-> IObservable<TResult>): IObservable<TResult>{
    return SelectManyObservable(this, selector)
}
