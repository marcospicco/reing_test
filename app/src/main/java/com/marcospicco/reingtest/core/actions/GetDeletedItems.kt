package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import com.marcospicco.reingtest.core.actions.DeleteItem.Companion.DELETED_ITEMS
import com.marcospicco.reingtest.core.utils.JsonUtils

class GetDeletedItems {

    fun doAction(prefs: SharedPreferences): MutableList<String>? {
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return JsonUtils.instance.fromJson(prefs.getString(DELETED_ITEMS, "") ?: "", type)
    }
}