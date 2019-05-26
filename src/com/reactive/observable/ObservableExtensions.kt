package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.observers.ObserverSubscribe
import com.reactive.observable.operators.SelectObservable
import com.reactive.observable.operators.WhereObservable
import java.lang.Exception

fun <T> IObservable<T>.subscribe() : Disposable{
    return this.subscribe(ObserverSubscribe.Create({ println("OnNext called from Stub")}, Stub.OnError, Stub.OnComplete))
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

class Stub {

    companion object {
        //val OnNext: (Any) -> Unit get() = { println("OnNext called from Stub")}
        //TODO understand why I cant use the generic version of it???

        val OnError: (Exception) -> Unit = {  println("onError called from Stub")}
        val OnComplete : () -> Unit get() = { println("onComplete called from Stub")}
    }
}

