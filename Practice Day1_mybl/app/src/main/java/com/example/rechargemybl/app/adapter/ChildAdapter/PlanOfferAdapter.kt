package com.example.rechargemybl.app.adapter.ChildAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.adapter.rcvAdapter
import com.example.rechargemybl.app.model.PlanOfferDao

class PlanOfferAdapter(val list : ArrayList<PlanOfferDao>) : RecyclerView.Adapter<PlanOfferAdapter.childHolder>() {
    class childHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val textView = itemView.findViewById<TextView>(R.id.typesOffer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): childHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planitem, parent, false)
        return childHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: childHolder, position: Int) {
        holder.textView.text = list[position].plan
    }
}