package com.reactive.observable.operators

import com.reactive.disposable.Disposable
import com.reactive.observable.IObservable
import com.reactive.observers.IObserver
import java.lang.Exception

class ToObservableObservable<T>: IObservable<T> {

    private val sources: Collection<T>

    constructor(sources: Collection<T>){
        this.sources = sources
    }

    override fun subscribe(observer: IObserver<T>): Disposable {
        return ToObservable(this, observer).run()
    }

    inner class ToObservable: OperatorObserverBase<T, T>{

        val parent: ToObservableObservable<T>

        constructor(parent: ToObservableObservable<T>, observer: IObserver<T>): super(observer){
            this.parent = parent
        }

        fun run(): Disposable{

            var iterator: Iterator<T>

            try {
                iterator = parent.sources.iterator()
            }
            catch (exception: Exception)
            {
                onError(exception)
                return Disposable.empty()
            }

            while (true){

                try {
                    if(iterator.hasNext()) {
                        observer.onNext(iterator.next())
                    }
                    else
                    {
                        try { onComplete() } finally { dispose() }
                        break
                    }
                }
                catch (exception: Exception)
                {
                    try { onError(exception) } finally { dispose() }
                    break
                }
            }

            return Disposable.empty()
        }

        override fun onNext(value: T) {
            //Do nothing here
        }

        override fun onComplete() {
            try { observer.onComplete() } finally { dispose() }
        }

        override fun onError(exception: Exception) {
            try { observer.onError(exception) } finally { dispose() }
        }

    }
}