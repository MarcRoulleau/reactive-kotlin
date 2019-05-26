package com.reactive.disposable

class EmptyDisposable : Disposable {

    override fun dispose() { println("Dispose method EmptyDisposable called")}
}

