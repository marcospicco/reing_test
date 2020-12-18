package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.marcospicco.reingtest.core.actions.SaveLocalList.Companion.FIND_DATA_LIST
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.utils.JsonUtils

class GetLocalList {

    fun doAction(prefs: SharedPreferences): ListModel {
        return JsonUtils.instance.fromJson(prefs.getString(FIND_DATA_LIST, "") ?: "", ListModel::class.java)
    }
}