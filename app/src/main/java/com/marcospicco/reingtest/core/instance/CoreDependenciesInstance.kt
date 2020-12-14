package com.marcospicco.reingtest.core.instance

import com.marcospicco.reingtest.core.actions.GetList
import com.marcospicco.reingtest.core.infrastructure.api.ListApi
import com.marcospicco.reingtest.core.infrastructure.api.ListService
import com.marcospicco.reingtest.core.utils.CoreSchedulerProvider
import com.marcospicco.reingtest.core.utils.SchedulerProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoreDependenciesInstance {

    fun resolveGetPaymentMethods(): GetList = GetList(resolveListService())

    fun resolveListService(): ListService = ListService(resolveListApi())

    fun resolveListApi(): ListApi = getRepository(ListApi::class.java)

    private fun <T> getRepository(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(service)
    }

    fun resolveScheduler(): SchedulerProvider = CoreSchedulerProvider()

    const val BASE_URL = "https://hn.algolia.com/"
    const val PLATFORM = "android"

}