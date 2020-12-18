package com.marcospicco.reingtest.core.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class StubSchedulerProvider : SchedulerProvider {

    private val testScheduler = TestScheduler()

    override fun io(): Scheduler {
        return testScheduler
    }

    override fun ui(): Scheduler {
        return testScheduler
    }

    fun triggerActions() {
        testScheduler.triggerActions()
    }
}