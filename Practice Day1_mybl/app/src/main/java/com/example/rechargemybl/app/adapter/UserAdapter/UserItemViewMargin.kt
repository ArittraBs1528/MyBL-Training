package com.example.rechargemybl.app.adapter.UserAdapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class UserItemViewMargin :RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 16
//        outRect.bottom = 10
//        outRect.left = 10
//        outRect.right = 10

    }
}