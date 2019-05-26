package com.reactive.observable.operators

import com.reactive.disposable.CompositeDisposable
import com.reactive.disposable.Disposable
import com.reactive.disposable.SingleAssignmentDisposable
import com.reactive.obervers.IObserver
import com.reactive.observable.IObservable
import java.lang.Exception
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock


// From one, select zero or more
class SelectManyObservable<TSource, TResult> : IObservable<TResult> {

    val source: IObservable<TSource>
    val selector: (TSource) -> IObservable<TResult>

    constructor(source: IObservable<TSource>, selector: (TSource) -> IObservable<TResult>){
        this.source = source
        this.selector = selector
    }

    override fun subscribe(observer: IObserver<TResult>): Disposable {
        return SelectManyOuterObserver(this, observer).run()
    }

     inner class SelectManyOuterObserver: OperatorObserverBase<TSource,TResult>{

        private val _parent: SelectManyObservable<TSource, TResult>
        private val composites: CompositeDisposable = CompositeDisposable()
         //parent gate
         //cancel disposable
         //paremt source

        constructor(parent: SelectManyObservable<TSource, TResult>, observer:IObserver<TResult>): super(observer){
            _parent = parent
        }

        fun run(): Disposable
        {
            val singleAssignment = SingleAssignmentDisposable()
            composites.add(singleAssignment)
            singleAssignment.disposable = _parent.source.subscribe(this)

            return composites
        }

        override fun OnNext(value: TSource) {

            val observable = _parent.selector(value)

            val singleAssignment = SingleAssignmentDisposable()
            composites.add(singleAssignment)

            val selectMany = SelectMany(this)
            singleAssignment.disposable = observable.subscribe(selectMany)

        }

        override fun onComplete() {
            //TODO send OnComplete only when there is only one disposable left in the composites collection
            if(composites.count == 1){
                super.observer.onComplete()
            }
        }

        override fun onError(exception: Exception) {
            val selectMany = SelectMany(this)
            selectMany.onError(exception)
        }

         inner class SelectMany : OperatorObserverBase<TResult, TResult>
         {
             val parent: SelectManyOuterObserver
             var lock = ReentrantLock()

             constructor(parent:SelectManyOuterObserver):super(parent.observer){
                 this.parent = parent
             }

             override fun OnNext(value: TResult) {
                 lock.withLock {
                     this.observer.OnNext(value)
                 }
             }

             override fun onComplete() {
                 //TODO send OnComplete only when there is only one disposable left in the composite collection
                 lock.withLock {
                     if(parent.composites.count == 1){
                         observer.onComplete()
                     }
                 }
             }

             override fun onError(exception: Exception) {
                 lock.withLock {
                     this.observer.onError(exception)
                 }
             }

         }

    }


}