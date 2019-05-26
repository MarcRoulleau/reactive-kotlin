package com.lvlstudio.disposable

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SerialDisposable: Disposable {

    private var current: Disposable? = null
    private val lock = ReentrantLock()
    private var isDisposed = false

    var disposable: Disposable?
        get() = current
        set(value) {
            var shouldDispose = false
            var old: Disposable? = null

            lock.withLock {
                shouldDispose = isDisposed
                if(!shouldDispose)
                {
                    old = current
                    current = value
                }
            }

            old?.dispose()

            if(shouldDispose){
                value?.dispose()
            }
        }

    override fun dispose() {
        var old: Disposable? = null

        lock.withLock {
            old = current
            current = null
        }

        old?.dispose()
    }
}