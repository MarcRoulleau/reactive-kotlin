package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.observers.IObserver

interface IObservable<T> {
    fun subscribe(observer: IObserver<T>): Disposable
}