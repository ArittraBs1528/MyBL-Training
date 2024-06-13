package com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GenericSliderItemMargin : RecyclerView.ItemDecoration() {

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
            outRect.left = 30
        } else if (position == itemCount - 1) {
            outRect.left = 22
            outRect.right = 30
        } else outRect.left = 22

    }
}