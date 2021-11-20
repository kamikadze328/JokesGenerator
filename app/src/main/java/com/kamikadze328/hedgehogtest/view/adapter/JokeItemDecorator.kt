package com.kamikadze328.hedgehogtest.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class JokeItemDecorator(
    @Px private val offsetBetween: Int = 0,
    @Px private val offsetHorizontal: Int = 0,
    @Px private val offsetBottomLast: Int = 0,
    @Px private val offsetTopFirst: Int = 0,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) return
        val itemCount = state.itemCount
        val isFirst = itemPosition == 0
        val isLast = itemPosition == itemCount - 1

        outRect.bottom = if (isLast) offsetBottomLast else offsetBetween
        outRect.top = if (isFirst) offsetTopFirst else offsetBetween
        outRect.left = offsetHorizontal
        outRect.right = offsetHorizontal
    }
}
