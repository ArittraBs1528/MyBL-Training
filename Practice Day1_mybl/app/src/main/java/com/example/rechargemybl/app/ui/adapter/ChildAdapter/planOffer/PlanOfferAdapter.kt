package com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.model.apiModel.Rail
import com.example.rechargemybl.app.ui.viewholder.ChildViewHolder

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

}