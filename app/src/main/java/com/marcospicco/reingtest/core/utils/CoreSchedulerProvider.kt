package com.marcospicco.reingtest.core.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CoreSchedulerProvider : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.newThread()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}