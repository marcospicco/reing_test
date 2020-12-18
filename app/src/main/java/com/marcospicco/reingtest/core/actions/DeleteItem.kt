package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import com.marcospicco.reingtest.core.utils.JsonUtils

class DeleteItem {

    fun doAction(prefs: SharedPreferences, id: String) {

        var data = ArrayList<String>()

        val type = object : TypeToken<ArrayList<String>>() {}.type
        prefs.getString(DELETED_ITEMS, "")?.let {
            data = JsonUtils.instance.fromJson(it, type) ?: data
        }

        data.add(id)

        prefs.edit().putString(DELETED_ITEMS, JsonUtils.instance.toJson(data)).apply()
    }

    companion object {
        const val DELETED_ITEMS = "deleted_items"
    }
}