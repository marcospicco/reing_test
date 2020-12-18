package com.marcospicco.reingtest.presentation.list.view.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcospicco.reingtest.R
import com.marcospicco.reingtest.core.domain.ListItemModel
import kotlinx.android.synthetic.main.row_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindType(context: Context, itemData: ListItemModel) {
        val onClickListener = context as OnClickListener
        itemView.apply {
            title.text = itemData.title
            description.text = StringBuilder()
                .append(itemData.author)
                .append(SPACE_LINE)
                .append(getTime(context, itemData.created)).toString()

            setOnClickListener {
                onClickListener.onClick(itemData.url)
            }
        }
    }

    // Deberia venir desde backend estos datos de manera correcta.
    private fun getTime(context: Context, created: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val createdTime = parser.parse(created)

        val currentTime: Date = Calendar.getInstance().time
        val diff = currentTime.time - createdTime.time

        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        var minutes = TimeUnit.MILLISECONDS.toMinutes(diff)

        lateinit var timeToShow: String

        if (hours < DAY_IN_HOURS) {
            if (hours > 0) {

                minutes -= TimeUnit.HOURS.toMinutes(hours)
                timeToShow = StringBuilder()
                    .append(hours)
                    .append(DOT)
                    .append(minutes)
                    .append(context.resources.getString(R.string.hour)).toString()
            } else {
                timeToShow = StringBuilder()
                    .append(minutes)
                    .append(context.resources.getString(R.string.minute)).toString()
            }
        } else if (hours < TWO_DAYS_IN_HOURS) {

            timeToShow = StringBuilder()
                .append(context.resources.getString(R.string.yesterday)).toString()
        } else {

            timeToShow = StringBuilder()
                .append(hours / DAY_IN_HOURS)
                .append(context.resources.getString(R.string.day)).toString()
        }

        return timeToShow
    }

    interface OnClickListener {
        fun onClick(link: String?)
    }

    companion object {
        const val DAY_IN_HOURS = 24
        const val TWO_DAYS_IN_HOURS = 48

        const val DOT = "."
        const val SPACE_LINE = " - "
    }
}