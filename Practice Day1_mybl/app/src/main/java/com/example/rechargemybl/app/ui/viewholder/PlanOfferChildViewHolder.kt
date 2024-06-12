package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.app.model.apiModel.Rail
import com.example.rechargemybl.databinding.PlanitemBinding

class PlanOfferChildViewHolder(val viewBinding: PlanitemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {


    companion object {
        fun create(parent: ViewGroup): PlanOfferChildViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val view = PlanitemBinding.inflate(inflater, parent, false)
            return PlanOfferChildViewHolder(view)
        }

    }

    fun bind(rail: Rail) {


        viewBinding.typesOffer.text = rail.titleEn
        if (rail.isHighlight == true) {
            viewBinding.dot.visibility = View.VISIBLE
        } else {
            viewBinding.dot.visibility = View.INVISIBLE
        }

        if (rail.icon.isNullOrEmpty()) {
            viewBinding.railsIcon.visibility = View.GONE
        } else {
            viewBinding.railsIcon.visibility = View.VISIBLE
            Glide.with(viewBinding.railsIcon.context)
                .load(rail.icon)
                .into(viewBinding.railsIcon)

        }


    }
}