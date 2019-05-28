package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.observable.operators.SelectManyObservable
import com.reactive.observers.ObserverSubscribe
import com.reactive.observable.operators.SelectObservable
import com.reactive.observable.operators.SkipObservable
import com.reactive.observable.operators.WhereObservable
import java.lang.Exception

fun <T> IObservable<T>.subscribe() : Disposable{
    return this.subscribe(ObserverSubscribe.Create({ println("_onNext called from Stub")}, Stub.OnError, Stub.OnComplete))
}

fun <T> IObservable<T>.subscribe(onNext: (T) -> Unit) : Disposable{
    return this.subscribe(ObserverSubscribe.Create(onNext, Stub.OnError, Stub.OnComplete))
}

fun <T> IObservable<T>.subscribe(onNext: (T) -> Unit, onError: (Exception) -> Unit) : Disposable{
    return this.subscribe(ObserverSubscribe.Create(onNext, onError, Stub.OnComplete))
}

fun <T> IObservable<T>.subscribe(onNext: (T) -> Unit, onError: (Exception) -> Unit, onComplete: () -> Unit) : Disposable{
    return this.subscribe(ObserverSubscribe.Create(onNext, onError, onComplete))
}

fun <T> IObservable<T>.where(predicate: (T) -> Boolean): IObservable<T>{

    if(this is WhereObservable)
        return this.combineWhere(predicate)

    return WhereObservable(this, predicate)
}

fun <TSource, TResult> IObservable<TSource>.select(selector: (TSource) -> TResult): IObservable<TResult>{
    return SelectObservable(this, selector)
}

fun <TSource, TResult> IObservable<TSource>.selectMany(selector: (TSource)-> IObservable<TResult>): IObservable<TResult>{
    return SelectManyObservable(this, selector)
}

fun <TSource> IObservable<TSource>.skip(count: Int): IObservable<TSource>{
    return SkipObservable(this, count)
}

class Stub {

    companion object {
        //val _onNext: (Any) -> Unit get() = { println("_onNext called from Stub")}
        //TODO understand why I cant use the generic version of it???

        val OnError: (Exception) -> Unit = {  println("onError called from Stub")}
        val OnComplete : () -> Unit get() = { println("onComplete called from Stub")}
    }
}

