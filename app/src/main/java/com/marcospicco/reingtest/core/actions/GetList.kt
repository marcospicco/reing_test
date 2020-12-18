package com.marcospicco.reingtest.core.actions

import android.content.Context
import android.content.SharedPreferences
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.infrastructure.api.ListService
import io.reactivex.Single

class GetList(
    private val listService: ListService,
    private val saveList: SaveLocalList,
    private val getDeletedItems: GetDeletedItems
) {

    fun doAction(prefs: SharedPreferences, platform: String): Single<ListModel> {
        return Single.fromCallable {
            val result = listService.getList(platform).toModel()
            val localDeletedItems = getDeletedItems.doAction(prefs)

            localDeletedItems?.let { deletedItems ->
                result.list
                    .filter { it.id in deletedItems }
                    .forEach { result.list.remove(it) }
            }

            saveList.doAction(prefs, result)

            return@fromCallable result
        }
    }
}