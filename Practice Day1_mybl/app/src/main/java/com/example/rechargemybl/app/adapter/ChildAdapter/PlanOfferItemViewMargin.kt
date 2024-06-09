package com.example.rechargemybl.app.adapter.ChildAdapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlanOfferItemViewMargin : RecyclerView.ItemDecoration() {

    var itemDecorationCount = 0;
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)


        //current position of the element
        val position = parent.getChildAdapterPosition(view)

        val itemCount = state.itemCount

        if (position == 0) {
            outRect.left = 55
        } else if (position == itemCount - 1) {
            outRect.left = 13
            outRect.right = 55
        } else outRect.left = 13

//

    }
}