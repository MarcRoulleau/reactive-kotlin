package com.reactive.disposable

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CompositeDisposable : Disposable {

    private var disposables: MutableList<Disposable> = mutableListOf()
    private var isDisposed = false
    private val lock = ReentrantLock()
    val count: Int
        get() = disposables.count()

    //TODO Not Thread safe
    fun add(item: Disposable)
    {
        var shouldDispose = false

        lock.withLock {
            shouldDispose = isDisposed

            if(!shouldDispose){
                disposables.add(item)

            }
        }

        if(shouldDispose){
            item.dispose()
        }
    }

    fun remove(disposable: Disposable){
        //Thread safe too
        //validate disposable is in collection of disposable

        var shouldDispose: Boolean
        lock.withLock {
            shouldDispose = isDisposed

            if(!shouldDispose){
                this.disposables.remove(disposable) //TODO validate if kotlin shrink the underlying array
            }
        }
    }


    override fun dispose() {

        var currentDisposables: MutableList<Disposable> = mutableListOf()

        lock.withLock {
            if(!isDisposed){
                currentDisposables = this.disposables.toMutableList()
                this.disposables.clear()
                isDisposed = true
            }
        }

        if(currentDisposables.count() > 0){
            for (item in currentDisposables){
                item.dispose()
            }
        }
    }

}