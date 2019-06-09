package com.reactive.observable.operators

import com.reactive.disposable.CompositeDisposable
import com.reactive.disposable.Disposable
import com.reactive.disposable.SingleAssignmentDisposable
import com.reactive.observable.IObservable
import com.reactive.observers.IObserver
import java.lang.Exception
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class MergeObservable<T> : IObservable<T>
{
    val sources: IObservable<IObservable<T>>

    constructor(source: IObservable<IObservable<T>>){
        this.sources = source
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        return MergeOuterObserver(this, observer).run()
    }

    inner class MergeOuterObserver : OperatorObserverBase<IObservable<T>, T>{

        val parent: MergeObservable<T>
        val composites = CompositeDisposable()
        val lock = ReentrantLock()

        constructor(parent: MergeObservable<T>, observer: IObserver<T>): super(observer)
        {
            this.parent = parent
        }

        fun run(): Disposable{

            val singleAssignment = SingleAssignmentDisposable()
            composites.add(singleAssignment)
            singleAssignment.disposable = parent.sources.subscribe(this)

            return composites
        }

        override fun onNext(value: IObservable<T>) {
            val singleAssignment = SingleAssignmentDisposable()
            composites.add(singleAssignment)

            val merge = Merge(this, singleAssignment)
            singleAssignment.disposable = value.subscribe(merge)
        }

        override fun onComplete() {

            if(composites.count == 1){
                try { observer.onComplete() } finally { dispose() }
            }
        }

        override fun onError(exception: Exception) {
            try { observer.onError(exception) } finally { dispose() }
        }

        inner class Merge : OperatorObserverBase<T, T>{

            val parent: MergeOuterObserver
            val cancel: Disposable

            constructor(parent: MergeOuterObserver, cancel: Disposable) : super(parent.observer)
            {
                this.parent = parent
                this.cancel = cancel
            }

            override fun onNext(value: T) {
                parent.lock.withLock {
                    observer.onNext(value)
                }
            }

            override fun onComplete()
            {
                parent.composites.remove(cancel)
                if (this.parent.composites.count == 0)
                {
                    parent.lock.withLock {
                        try { observer.onComplete() } finally { dispose() }
                    }
               }
            }

            override fun onError(exception: Exception) {
                parent.lock.withLock {
                    try { observer.onError(exception) } finally { dispose() }
                }
            }
        }
    }
}