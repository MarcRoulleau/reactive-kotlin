package com.lvlstudio.disposable

class AnonymousDisposable: Disposable {

    val _action: () -> Unit
    constructor(action: () -> Unit){
        _action = action
    }

    private var _isDisposed = false

    override fun dispose() {

        if(!_isDisposed){
            _isDisposed = true
            _action()
        }
    }

}