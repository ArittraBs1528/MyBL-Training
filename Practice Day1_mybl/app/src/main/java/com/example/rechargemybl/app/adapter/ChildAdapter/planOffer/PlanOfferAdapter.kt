package com.example.rechargemybl.app.adapter.ChildAdapter.planOffer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.app.model.apiModel.Rail
import com.example.rechargemybl.databinding.PlanitemBinding

class PlanOfferAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val childDataSet = ArrayList<Rail>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ChildViewHolder.create(parent)

    }

    override fun getItemCount(): Int {
        return childDataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChildViewHolder -> childDataSet.getOrNull(position)?.let { holder.bind(it) }

        }
    }

    fun submitData(submittedItem: List<Rail>?) {
        val oldData = ArrayList(childDataSet)
        childDataSet.clear()
        if (submittedItem != null) {
            childDataSet.addAll(submittedItem)
        }

        val diffUtilCallBack = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldData.size
            }

            override fun getNewListSize(): Int {
                return childDataSet.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return childDataSet[newItemPosition].id == oldData[oldItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return childDataSet[newItemPosition] == oldData[oldItemPosition]
            }

        }
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        diffResult.dispatchUpdatesTo(this)
    }

    class ChildViewHolder(val viewBinding: PlanitemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {


        companion object {
            fun create(parent: ViewGroup): ChildViewHolder {

                val inflater = LayoutInflater.from(parent.context)
                val view = PlanitemBinding.inflate(inflater, parent, false)
                return ChildViewHolder(view)
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
}