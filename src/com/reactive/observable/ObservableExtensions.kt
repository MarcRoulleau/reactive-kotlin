package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.observable.operators.*
import com.reactive.observers.ObserverSubscribe
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

fun <T> MutableList<T>.toObservable(): IObservable<T>{
        return ToObservableObservable(this)
}

fun <T> Collection<T>.toObservable(): IObservable<T>{
    return ToObservableObservable(this)
}

class Stub {

    companion object {
        //val _onNext: (Any) -> Unit get() = { println("_onNext called from Stub")}
        //TODO understand why I cant use the generic version of it???

        val OnError: (Exception) -> Unit = {  println("onError called from Stub")}
        val OnComplete : () -> Unit get() = { println("onComplete called from Stub")}
    }
}

