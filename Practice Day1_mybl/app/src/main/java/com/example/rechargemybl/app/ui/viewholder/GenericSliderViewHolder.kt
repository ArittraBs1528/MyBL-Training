package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.model.apiModel.SlideData
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.GenericSliderChildAdapter
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.PlanOfferItemViewMargin
import com.example.rechargemybl.databinding.GenericSliderBinding

class GenericSliderViewHolder(private val viewBinding: GenericSliderBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private val marginLayout = PlanOfferItemViewMargin()
    private val layoutManager =
        LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
    private val genericSliderChildAdapter = GenericSliderChildAdapter()

    init {
        viewBinding.genericRcv.layoutManager = layoutManager
        viewBinding.genericRcv.addItemDecoration(marginLayout)
        viewBinding.genericRcv.adapter = genericSliderChildAdapter
    }

    companion object {
        fun create(parent: ViewGroup): GenericSliderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = GenericSliderBinding.inflate(inflater, parent, false)
            return GenericSliderViewHolder(view)
        }
    }

    fun bind(listItems: List<SlideData>?) {

//        viewBinding.sobDekhun.
        genericSliderChildAdapter.submitData(listItems)
    }


}
