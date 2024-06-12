package com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.model.apiModel.SlideData

import com.example.rechargemybl.app.ui.viewholder.GenericSliderChildViewHolder

class GenericSliderChildAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val childDataSet = ArrayList<SlideData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GenericSliderChildViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return childDataSet.size
    }
    fun submitData(submittedItem: List<SlideData>?) {
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


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenericSliderChildViewHolder -> childDataSet.getOrNull(position)?.let { holder.bind(it) }

        }
    }
}