package com.example.rechargemybl.app.ui.adapter.mainAdapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class UserItemViewMargin : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        if (position != itemCount - 1) {

            outRect.bottom = 18
        }
//        outRect.top = 16

//        outRect.left = 10
//        outRect.right = 10

    }
}