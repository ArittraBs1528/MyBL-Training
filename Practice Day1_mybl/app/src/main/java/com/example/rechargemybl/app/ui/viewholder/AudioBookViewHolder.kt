package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.databinding.ItemsViewBillsBinding

class AudioBookViewHolder(private val viewBinding: ItemsViewBillsBinding) :
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



        viewBinding.seeAll.text = bills.cta?.nameBn
        if (bills.isTitleShow) {
            viewBinding.titles.text = bills.titleBn
            Glide.with(viewBinding.icon.context)
                .load(bills.icon).into(viewBinding.icon)
        }


        if (bills.cta == null) {
            viewBinding.grp.visibility = View.GONE
        }


    }

}