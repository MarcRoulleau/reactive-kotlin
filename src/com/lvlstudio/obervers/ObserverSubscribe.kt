package com.lvlstudio.obervers

class ObserverSubscribe<T>: IObserver<T> {

    val onNext : (T) -> Unit
    val onError : (Exception) -> Unit
    val onComplete : () -> Unit

    companion object {

        fun <T> Create(onNext: (T) -> Unit, onError: (Exception) -> Unit, onComplete: () -> Unit): IObserver<T> {
            return ObserverSubscribe(onNext, onError, onComplete)
        }
    }

    constructor(onNext: (T) -> Unit, onError: (Exception) -> Unit, onComplete: () -> Unit){
        this.onNext = onNext
        this.onError = onError
        this.onComplete = onComplete
    }

    override fun OnNext(value: T) {
        onNext(value)
    }

    override fun onComplete() {
       onComplete()
    }

    override fun onError(exception: Exception) {
        onError(exception)
    }

}