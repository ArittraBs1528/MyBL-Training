package com.example.rechargemybl.app.adapter.ChildAdapter.childBillAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.adapter.ChildAdapter.planOffer.PlanOfferAdapter
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.app.model.apiModel.Rail
import com.example.rechargemybl.databinding.ItemsViewBillsBinding

class childBillAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val childDataSet = ArrayList<Data>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PlanOfferAdapter.ChildViewHolder.create(parent)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun submitData(submittedItem: List<Data>?) {
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


    class BillViewHolder(val viewBinding: ItemsViewBillsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): BillViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemsViewBillsBinding.inflate(inflater, parent, false)
                return BillViewHolder(view)
            }
        }

        fun bind(data : Data){
//            val
        }
    }
}