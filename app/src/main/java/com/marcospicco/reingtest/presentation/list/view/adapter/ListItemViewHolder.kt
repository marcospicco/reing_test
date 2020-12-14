package com.marcospicco.reingtest.presentation.list.view.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcospicco.reingtest.core.domain.ListItemModel
import kotlinx.android.synthetic.main.row_list_item.view.*

class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindType(context: Context, itemData: ListItemModel) {
        val onClickListener = context as OnClickListener
        itemView.apply {
            title.text = itemData.title
            description.text = StringBuilder()
                .append(itemData.author)
                .append(" - ")
                .append(itemData.created).toString()

            setOnClickListener {
                onClickListener.onClick(itemData.url)
            }
        }
    }

    interface OnClickListener {
        fun onClick(link: String?)
    }
}