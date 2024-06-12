package com.example.rechargemybl.app.ui.adapter.mainAdapter

import android.util.Log

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.app.Utility.Helpers.TYPE_BALANCE
import com.example.rechargemybl.app.Utility.Helpers.TYPE_AUDIOBOOK
import com.example.rechargemybl.app.Utility.Helpers.TYPE_LIVE_RADIO
import com.example.rechargemybl.app.Utility.Helpers.TYPE_GENERIC_RAIL
import com.example.rechargemybl.app.Utility.Helpers.TYPE_GENERIC_SLIDER
import com.example.rechargemybl.app.Utility.Helpers.typeMap
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.app.model.apiModel.BalanceCard
import com.example.rechargemybl.app.model.apiModel.SlideData
import com.example.rechargemybl.app.ui.viewholder.AudioBookViewHolder
import com.example.rechargemybl.app.ui.viewholder.BalanceViewHolder
import com.example.rechargemybl.app.ui.viewholder.GenericSliderViewHolder
import com.example.rechargemybl.app.ui.viewholder.LiveRadioViewHolder
import com.example.rechargemybl.app.ui.viewholder.PlanOfferViewHolder
import com.example.rechargemybl.app.utility.toObject


class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<Data>()

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        val type = dataSet[position].componentKey
        return typeMap[type] ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        Log.wtf("Aritra", "onCreateViewHolder: $viewType")

        val key = typeMap.filterValues { it == viewType }.keys.first()

        when (key) {
            TYPE_BALANCE -> {
                return BalanceViewHolder.create(parent)
            }

            TYPE_GENERIC_RAIL -> {
                return PlanOfferViewHolder.create(parent)
            }

            TYPE_AUDIOBOOK -> {
                return AudioBookViewHolder.create(parent)
            }


            TYPE_LIVE_RADIO -> {
                return LiveRadioViewHolder.create(parent)
            }
            TYPE_GENERIC_SLIDER->{
                return GenericSliderViewHolder.create(parent)
            }

        }

        return BalanceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.wtf("aritra", "onBindViewHolder: $position")

        when (holder) {
            is BalanceViewHolder -> dataSet.getOrNull(position)?.data?.let {
                holder.bind(
                        (it as? Map<String, Any?>)?.toObject<BalanceCard>(),


                )
            }

            is AudioBookViewHolder -> dataSet.getOrNull(position)?.let { holder.bind(it) }
            is LiveRadioViewHolder -> dataSet.getOrNull(position)?.let { holder.bind(it) }
            is PlanOfferViewHolder -> holder.bind(dataSet.getOrNull(position)?.rails)
            is GenericSliderViewHolder -> dataSet.getOrNull(position)?.data?.let{
                holder.bind(
                    (it as? ArrayList<Map<String,Any>>)?.map{it.toObject<SlideData>()},
                    dataSet.getOrNull(position)?.icon
                )
            }
        }
    }

    fun submitData(people: List<Data>) {
        val oldData = ArrayList(dataSet)  //creates a copy only
        dataSet.clear()
        dataSet.addAll(people)

        val diffUtilCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldData.size
            }

            override fun getNewListSize(): Int {
                return dataSet.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldData[oldItemPosition].id == dataSet[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldData[oldItemPosition] == dataSet[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
    }


}


