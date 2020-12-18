package com.marcospicco.reingtest.presentation.list.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.marcospicco.reingtest.R
import com.marcospicco.reingtest.presentation.list.view.adapter.ListAdapter

class SwiperItemTouchHelper(
    private val dragDirs: Int,
    private val swipeDirs: Int,
    private val context: Context,
    private val swipeEventListener: SwipeEventListener
): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
        swipeEventListener.onSwipe(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val colorDrawableBackground = ColorDrawable(context.resources.getColor(R.color.red))

        colorDrawableBackground.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )

        colorDrawableBackground.draw(c)

        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = context.resources.getDimension(R.dimen.delete_text_size)
        paint.textAlign = Paint.Align.CENTER

        val textPosY = ((itemView.top.toFloat() + itemView.bottom.toFloat()) / 2) +
                context.resources.getDimension(R.dimen.delete_text_size) / 4
        val textPosX = (itemView.right - context.resources.getDimension(R.dimen.delete_margin_rigth_text))

        c.drawText(context.resources.getString(R.string.item_delete_action), textPosX, textPosY, paint)

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    interface SwipeEventListener {
        fun onSwipe(position: Int)
    }
}