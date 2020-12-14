package com.marcospicco.reingtest.core.infrastructure.representation

import com.google.gson.annotations.SerializedName
import com.marcospicco.reingtest.core.domain.ListItemModel
import com.marcospicco.reingtest.core.domain.ListModel

data class ListRepresentation(
    @SerializedName("hits") val list: MutableList<ListItemRepresentation>? = null,
) {
    fun toModel(): ListModel {
        return ListModel(ListItemRepresentation.toModel(list))
    }
}

data class ListItemRepresentation(
    @SerializedName("story_title") private val storyTitle: String?,
    @SerializedName("title") private val title: String?,
    @SerializedName("author") private val author: String,
    @SerializedName("created_at") private val created: String,
    @SerializedName("story_url") private val storyUrl: String?,
    @SerializedName("url") private val url: String?
) {
    fun toModel(): ListItemModel {
        return ListItemModel(storyTitle ?: title, author, created, storyUrl ?: url)
    }

    companion object {
        fun toModel(listRepresentation: MutableList<ListItemRepresentation>?): MutableList<ListItemModel>? {
            val listItemModel=  mutableListOf<ListItemModel>()
            listRepresentation?.forEach {
                listItemModel.add(it.toModel())
            }
            return listItemModel
        }
    }
}