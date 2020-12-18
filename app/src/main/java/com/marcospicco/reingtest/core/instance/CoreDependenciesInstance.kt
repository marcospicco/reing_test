package com.marcospicco.reingtest.core.instance

import android.content.Context
import android.content.SharedPreferences
import com.marcospicco.reingtest.core.actions.*
import com.marcospicco.reingtest.core.infrastructure.api.ListApi
import com.marcospicco.reingtest.core.infrastructure.api.ListService
import com.marcospicco.reingtest.core.utils.CoreSchedulerProvider
import com.marcospicco.reingtest.core.utils.SchedulerProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreDependenciesInstance {

    fun resolveGetList() = GetList(resolveListService(), resolveSaveList(), resolveGetDeleteItems())

    fun resolveGetLocalList() = GetLocalList(resolveGetDeleteItems())

    fun resolveSaveList() = SaveLocalList()

    fun resolveGetDeleteItems() = GetDeletedItems()

    fun resolveDeleteItem() = DeleteItem()

    fun resolveListService() = ListService(resolveListApi())

    fun resolveSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun resolveListApi(): ListApi = getRepository(ListApi::class.java)

    private fun <T> getRepository(service: Class<T>): T {
        
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(service)
    }

    fun resolveScheduler(): SchedulerProvider = CoreSchedulerProvider()

    const val BASE_URL = "https://hn.algolia.com/"
    const val PLATFORM = "android"

}