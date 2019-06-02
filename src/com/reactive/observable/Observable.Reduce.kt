package com.reactive.observable

import com.reactive.observable.operators.SkipObservable
import com.reactive.observable.operators.TakeObservable
import com.reactive.observable.operators.WhereObservable

fun <T> IObservable<T>.where(predicate: (T) -> Boolean): IObservable<T>{

    if(this is WhereObservable)
        return this.combineWhere(predicate)

    return WhereObservable(this, predicate)
}

fun <TSource> IObservable<TSource>.skip(count: Int): IObservable<TSource>{
    return SkipObservable(this, count)
}

fun <TSource> IObservable<TSource>.take(count: Int): IObservable<TSource>{
    return TakeObservable(this, count)
}