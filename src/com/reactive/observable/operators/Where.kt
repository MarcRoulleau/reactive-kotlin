package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception

class WhereObservable<T> : IObservable<T>
{
    val predicate: (T)-> Boolean
    private val _source: IObservable<T>

    constructor(source: IObservable<T>, predicate: (T)-> Boolean){
        _source = source
        this.predicate = predicate
    }

    fun combineWhere(combinedPredicate: (T) -> Boolean): IObservable<T>{

        val newPredicate: (T) -> Boolean = {x -> predicate(x) && combinedPredicate(x)}
        return WhereObservable(_source, newPredicate)
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        return _source.subscribe(WhereObserver(this, observer))
    }

    inner class WhereObserver<T>: OperatorObserverBase<T, T> {

        private val _parent: WhereObservable<T>
        private val _observer: IObserver<T>

        constructor(parent: WhereObservable<T>, observer: IObserver<T>) :super(observer){
           _parent = parent
            _observer = observer
        }

        override fun onNext(value: T) {

            var isPassed = false
            try {
                isPassed = _parent.predicate(value)

            } catch (exception: Exception){
                _observer.onError(exception)
                dispose()
            }

            if(isPassed){
                _observer.onNext(value)
            }
        }

        override fun onError(exception: Exception) {
            try { observer.onError(exception) } finally { dispose() }
        }

        override fun onComplete() {
            try { observer.onComplete() } finally { dispose() }
        }

    }
}