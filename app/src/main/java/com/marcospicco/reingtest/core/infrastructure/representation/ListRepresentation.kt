package com.marcospicco.reingtest.core.infrastructure.representation

import com.google.gson.annotations.SerializedName
import com.marcospicco.reingtest.core.domain.ListItemModel
import com.marcospicco.reingtest.core.domain.ListModel
import java.text.SimpleDateFormat
import java.util.*

data class ListRepresentation(
    @SerializedName("hits") val list: MutableList<ListItemRepresentation>? = null,
) {
    fun toModel(): ListModel {
        return ListModel(ListItemRepresentation.toModel(list))
    }
}

data class ListItemRepresentation(
    @SerializedName("objectID") private val id: String,
    @SerializedName("story_title") private val storyTitle: String?,
    @SerializedName("title") private val title: String?,
    @SerializedName("author") private val author: String,
    @SerializedName("created_at") private val created: String,
    @SerializedName("story_url") private val storyUrl: String?,
    @SerializedName("url") private val url: String?
) {
    fun toModel(): ListItemModel {
        return ListItemModel(id, storyTitle ?: title, author, created, storyUrl ?: url)
    }

    companion object {
        fun toModel(listRepresentation: MutableList<ListItemRepresentation>?): MutableList<ListItemModel> {
            val listItemModel = mutableListOf<ListItemModel>()

            // Deberia venir ordenado desde backend
            listRepresentation?.apply {
                sortByDescending { it.created.toDate() }
                forEach {
                    listItemModel.add(it.toModel())
                }
            }
            return listItemModel
        }
    }
}

fun String.toDate(): Date {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(this)
}