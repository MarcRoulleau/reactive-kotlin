package com.reactive.observable

import com.reactive.observable.operators.MergeObservable

fun <TSource> IObservable<TSource>.merge(sources: MutableList<IObservable<TSource>>): IObservable<TSource>{
    return MergeObservable(sources.toObservable())
}

fun <TSource> IObservable<TSource>.merge(vararg sources: IObservable<TSource>): IObservable<TSource>{
    var sequences = mutableListOf(this)
    sequences.addAll(sources)

    return MergeObservable(sequences.toObservable())
}

