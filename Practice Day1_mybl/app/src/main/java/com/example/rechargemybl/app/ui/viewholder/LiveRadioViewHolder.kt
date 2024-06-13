package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.databinding.ItemsViewBillsBinding

class LiveRadioViewHolder(private val viewBinding: ItemsViewBillsBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        fun create(parent: ViewGroup): AudioBookViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = ItemsViewBillsBinding.inflate(inflater, parent, false)
            return AudioBookViewHolder(view)
        }
    }


    fun bind(bills: Data) {

        Glide.with(viewBinding.cartInImage.context)
            .load(bills.banner)
            .into(viewBinding.cartInImage)

        Glide.with(viewBinding.icon.context)
            .load(bills.icon).into(viewBinding.icon)

        viewBinding.seeAll.text = bills.cta?.nameBn
        viewBinding.titles.text = bills.titleBn

        if (bills.isTitleShow == true) {
            viewBinding.titles.visibility = View.VISIBLE
            viewBinding.icon.visibility = View.VISIBLE
        } else {
            viewBinding.titles.visibility = View.GONE
            viewBinding.icon.visibility = View.GONE
        }


        viewBinding.seeAll.text = bills.cta?.nameEn

        if (bills.cta == null) {
            viewBinding.ctaGrpBills.visibility = View.GONE
        } else {
            viewBinding.ctaGrpBills.visibility = View.VISIBLE
        }
    }

}