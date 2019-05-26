package com.lvlstudio.disposable

class EmptyDisposable : Disposable {

    override fun dispose() { println("Dispose method EmptyDisposable called")}
}

