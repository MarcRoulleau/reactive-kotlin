package com.reactive.tests

import com.reactive.disposable.Disposable
import com.reactive.observable.Observable
import com.reactive.observable.select
import com.reactive.observable.subscribe
import com.reactive.observable.selectMany
import org.junit.Test

class SelectManyTests {

    @Test fun simple_selectMany(){

        Observable
            .generate(0, { x -> x < 5 }, { x -> x + 1 }, { x -> x })
            .selectMany { x -> Observable.create<String> {
                    println("VALUE $x")
                    Disposable.create { println("Dispose called") }
                }
            }
            .subscribe()
    }
}