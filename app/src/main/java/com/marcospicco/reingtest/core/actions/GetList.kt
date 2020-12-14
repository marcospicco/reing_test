package com.marcospicco.reingtest.core.actions

import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.infrastructure.api.ListService
import io.reactivex.Single

class GetList(private val listService: ListService) {

    fun doAction(platform: String): Single<ListModel> {
        return Single.fromCallable {
            return@fromCallable listService.getList(platform).toModel()
        }
    }
}