package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception


//TODO add optimization with SelectWhereObservable
class SelectObservable<TSource, TResult> : IObservable<TResult> {

    private val source: IObservable<TSource>
    val selector: (TSource) -> TResult

    constructor(source: IObservable<TSource>, selector: (TSource) -> TResult ){
        this.source = source
        this.selector = selector
    }

    override fun subscribe(observer: IObserver<TResult>): Disposable {
        var selectObserver = SelectObserver(observer, this)
        return this.source.subscribe(selectObserver)
    }

        inner class SelectObserver<TSource, TResult>: OperatorObserverBase<TSource, TResult>{

            val parent: SelectObservable<TSource, TResult>

            constructor(observer: IObserver<TResult>, parent: SelectObservable<TSource, TResult>): super(observer){
                this.parent = parent
            }

            override fun onNext(value: TSource) {
                try {
                    var result = this.parent.selector(value)
                    observer.onNext(result)
                }finally {
                    dispose()
                }
            }

            override fun onComplete() {
                try {
                    observer.onComplete()
                }finally {
                    dispose()
                }
            }

            override fun onError(exception: Exception) {
                try {
                    observer.onError(exception)
                }finally {
                    dispose()
                }
            }
        }
}