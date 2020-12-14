package com.marcospicco.reingtest.core.infrastructure.api

import com.marcospicco.reingtest.core.infrastructure.representation.ListRepresentation

class ListService(private val api: ListApi) {

    fun getList(platform: String): ListRepresentation {

        val response = api.getList(platform).execute()
        if (response.isSuccessful) {
            return response.body() ?: ListRepresentation()
        }
        throw RuntimeException()

    }
}