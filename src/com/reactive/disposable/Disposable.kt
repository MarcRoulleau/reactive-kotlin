package com.reactive.disposable


interface Disposable {
    fun dispose()

    companion object {
        fun empty() : Disposable {
            return EmptyDisposable()
        }

        fun create(action: () -> Unit): Disposable{
            return AnonymousDisposable(action)
        }
    }
}