package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.marcospicco.reingtest.core.actions.SaveLocalList.Companion.FIND_DATA_LIST
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.utils.JsonUtils

class GetLocalList(private val getDeletedItems: GetDeletedItems) {

    fun doAction(prefs: SharedPreferences): ListModel {

        val localDeletedItems = getDeletedItems.doAction(prefs)
        val result = JsonUtils.instance.fromJson(prefs.getString(FIND_DATA_LIST, "") ?: "", ListModel::class.java)

        localDeletedItems?.let { deletedItems ->
            result.list
                .filter { it.id in deletedItems }
                .forEach { result.list.remove(it) }
        }

        return result
    }
}