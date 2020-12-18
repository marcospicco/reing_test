package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.utils.JsonUtils

class SaveLocalList {

    fun doAction(prefs: SharedPreferences, list: ListModel) {
        prefs.edit().putString(FIND_DATA_LIST, JsonUtils.instance.toJson(list)).apply()
    }

    companion object {
        const val FIND_DATA_LIST = "list"
    }
}