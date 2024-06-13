package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.R
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

        Glide.with(viewBinding.img.context)
            .load(items.image_url)
            .into(viewBinding.img)

        viewBinding.subTitle.text = items.subtitle_text_en?:itemView.context.getString(R.string.NoTitile)
        viewBinding.title.text = items.title?:itemView.context.getString(R.string.NoTitile)

    }

}
