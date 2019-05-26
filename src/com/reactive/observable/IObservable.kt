package com.reactive.observable

import com.reactive.disposable.Disposable
import com.reactive.obervers.IObserver

interface IObservable<T> {
    fun subscribe(observer: IObserver<T>): Disposable
}