package com.marcospicco.reingtest.core.infrastructure.api

import com.marcospicco.reingtest.core.infrastructure.representation.ListRepresentation
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface ListApi {

    @GET("api/v1/search_by_date")
    fun getList(
        @Query("query") platform: String
    ): Call<ListRepresentation>
}