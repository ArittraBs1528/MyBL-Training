package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.model.apiModel.SlideData
import com.example.rechargemybl.databinding.GenericItemViewBinding
import com.example.rechargemybl.databinding.PlanitemBinding

class GenericSliderChildViewHolder(val viewBinding: GenericItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root){


    companion object {
        fun create(parent: ViewGroup): GenericSliderChildViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val view = GenericItemViewBinding.inflate(inflater, parent, false)
            return GenericSliderChildViewHolder(view)
        }

    }
    fun bind(items: SlideData) {
        viewBinding.subTitle.text = items.banner_text_bn
    }

}
