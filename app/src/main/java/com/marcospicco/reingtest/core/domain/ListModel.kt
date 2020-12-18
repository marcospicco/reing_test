package com.marcospicco.reingtest.core.domain

data class ListModel(
    val list: MutableList<ListItemModel>
)

data class ListItemModel(
    val id: String,
    val title: String?,
    val author: String,
    val created: String,
    val url: String?
)