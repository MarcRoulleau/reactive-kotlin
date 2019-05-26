package com.lvlstudio.observable.operators

import com.lvlstudio.disposable.Disposable
import com.lvlstudio.obervers.IObserver
import com.lvlstudio.observable.IObservable
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

        override fun OnNext(value: T) {

            var isPassed = false
            try {
                isPassed = _parent.predicate(value)

            } catch (exception: Exception){
                _observer.onError(exception)
                dispose()
            }

            if(isPassed){
                _observer.OnNext(value)
            }
        }

        override fun onComplete() {
           try {
               _observer.onComplete()
           } finally {
               dispose()
           }
        }

        override fun onError(exception: Exception) {
           try {
               _observer.onError(exception)
           }finally {
               dispose()
           }

        }

    }
}