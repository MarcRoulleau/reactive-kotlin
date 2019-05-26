package com.lvlstudio.disposable

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SingleAssignmentDisposable: Disposable {

    private var current: Disposable? = null
    private val lock = ReentrantLock()
    private val isDisposed = false

    var disposable: Disposable
        get() = current ?: Disposable.empty()
        set(value) {

            var shouldDisposed = false
            var old: Disposable? = null
            lock.withLock {
                shouldDisposed = isDisposed

                if(!shouldDisposed){

                    if(current != null)
                        throw IllegalStateException("Disposable has already been assigned")

                    old = current
                    current = value
                }
            }

            old?.dispose()

            if(shouldDisposed){
                value.dispose()
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