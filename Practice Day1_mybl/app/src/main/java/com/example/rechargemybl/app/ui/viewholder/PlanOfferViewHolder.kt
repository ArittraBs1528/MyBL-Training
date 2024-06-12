package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.model.apiModel.Rail
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.PlanOfferChildAdapter
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.PlanOfferItemViewMargin
import com.example.rechargemybl.databinding.PlanandofferBinding

class PlanOfferViewHolder(private val viewBinding: PlanandofferBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private val marginLayout = PlanOfferItemViewMargin()
    private val layoutManager =
        LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
    private val planOfferAdapter = PlanOfferChildAdapter()

    companion object {
        fun create(parent: ViewGroup): PlanOfferViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = PlanandofferBinding.inflate(inflater, parent, false)
            return PlanOfferViewHolder(view)
        }
    }

    init {
        viewBinding.planRcv.layoutManager = layoutManager
        viewBinding.planRcv.addItemDecoration(marginLayout)
        viewBinding.planRcv.adapter = planOfferAdapter
    }

    fun bind(planOfferList: List<Rail>?) {


        if (planOfferList != null) {
            planOfferAdapter.submitData(planOfferList)
        }
    }
}