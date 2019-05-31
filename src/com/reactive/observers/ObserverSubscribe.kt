package com.reactive.observers

class ObserverSubscribe<T>: ObserverBase<T> {

    private val _onNext : (T) -> Unit
    private val _onError : (Exception) -> Unit
    private val _onComplete : () -> Unit

    companion object {

        fun <T> Create(onNext: (T) -> Unit, onError: (Exception) -> Unit, onComplete: () -> Unit): IObserver<T> {
            return ObserverSubscribe(onNext, onError, onComplete)
        }
    }

    constructor(onNext: (T) -> Unit, onError: (Exception) -> Unit, onComplete: () -> Unit){
        _onNext = onNext
        _onError = onError
        _onComplete = onComplete
    }

    override fun onNextCore(value: T) {
        _onNext(value)
    }

    override fun onCompleteCore() {
       _onComplete()
    }

    override fun onErrorCore(exception: Exception) {
        _onError(exception)
    }

}