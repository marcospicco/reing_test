package com.marcospicco.reingtest.presentation.list.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcospicco.reingtest.R
import com.marcospicco.reingtest.core.domain.ListItemModel

class ListAdapter(private val context: Context) :
    RecyclerView.Adapter<ListItemViewHolder>() {

    lateinit var list: MutableList<ListItemModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindType(context, list.get(position))
    }

    override fun getItemCount() = list.size

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(newList: MutableList<ListItemModel>) {
        list = newList
        notifyDataSetChanged()
    }
}