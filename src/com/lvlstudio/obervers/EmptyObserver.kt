package com.lvlstudio.obervers

import java.lang.Exception

class EmptyObserver<T> : IObserver<T> {

    companion object {
        fun <T> instance() : EmptyObserver<T> {
           return EmptyObserver()
        }
    }

    override fun OnNext(value: T) { }

    override fun onComplete() { }

    override fun onError(exception: Exception) { }
}