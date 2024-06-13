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


    fun bind(audiobookData: Data) {

        Glide.with(viewBinding.cartInImage.context)
            .load(audiobookData.banner)
            .into(viewBinding.cartInImage)

        viewBinding.seeAll.text = audiobookData.cta?.nameBn
        viewBinding.titles.text = audiobookData.titleBn
        Glide.with(viewBinding.icon.context)
            .load(audiobookData.icon).into(viewBinding.icon)

        if (audiobookData.isTitleShow == true) {
            viewBinding.titles.visibility = View.VISIBLE
            viewBinding.icon.visibility = View.VISIBLE
        }else{
            viewBinding.titles.visibility = View.GONE
            viewBinding.icon.visibility = View.GONE
        }


        if (audiobookData.cta == null) {
            viewBinding.ctaGrp.visibility = View.GONE
        }else{
            viewBinding.ctaGrp.visibility = View.VISIBLE
        }


    }

}