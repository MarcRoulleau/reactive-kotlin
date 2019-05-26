package com.lvlstudio.observable

import com.lvlstudio.disposable.Disposable
import com.lvlstudio.obervers.IObserver

interface IObservable<T> {
    fun subscribe(observer: IObserver<T>): Disposable
}