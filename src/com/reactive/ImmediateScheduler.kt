package com.reactive

import com.reactive.disposable.Disposable
import java.time.Duration

class ImmediateScheduler<TState> : IScheduler<TState> {

    override fun schedule(state: TState, duration: Duration, action: (TState) -> Disposable): Disposable {

        //Sleep for a time
        Thread.sleep(duration.toMillis())

        return action(state)
    }

}

interface IScheduler<TState> {
     fun schedule(state: TState, duration: Duration, action: (TState) -> Disposable): Disposable
}