package com.example.rechargemybl.app.adapter.ChildAdapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlanOfferItemViewMargin  : RecyclerView.ItemDecoration() {

    var itemDecorationCount =0;
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.top = 16
//        outRect.bottom = 10
        outRect.left = 60
//        outRect.right = 10
        itemDecorationCount++
    }
}