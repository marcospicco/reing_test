package com.marcospicco.reingtest.core.utils

import io.reactivex.Scheduler

interface SchedulerProvider {

    /**
     * @return a `Scheduler` that specifies in what thread should the IO actions be done in
     * @see Scheduler
     */
    fun io(): Scheduler

    /**
     * @return a `Scheduler` that specifies in what thread should the UI actions be done in
     * @see Scheduler
     */
    fun ui(): Scheduler
}