package com.marcospicco.reingtest.core.domain

data class ListModel(
    val list: MutableList<ListItemModel>? = null,
)

data class ListItemModel(
    val title: String?,
    val author: String,
    val created: String,
    val url: String?
)