package com.example.rechargemybl.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.model.UserDao
import com.google.android.material.imageview.ShapeableImageView

class rcvAdapter(val arrayList: ArrayList<UserDao>) : RecyclerView.Adapter<rcvAdapter.rcvHolder>() {


    class rcvHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crntBalance: TextView = itemView.findViewById<TextView>(R.id.balance)
        val validBalance: TextView = itemView.findViewById<TextView>(R.id.validText)
        val internetAmount: TextView = itemView.findViewById<TextView>(R.id.internet_amount)
        val internetUnit: TextView = itemView.findViewById<TextView>(R.id.internet_unit)
        val minAmount: TextView = itemView.findViewById<TextView>(R.id.minute_amount)
        val minSec: TextView = itemView.findViewById<TextView>(R.id.min_sec)
        val smsAmount: TextView = itemView.findViewById<TextView>(R.id.sms_amount)
        val due_Loan_amount: TextView = itemView.findViewById<TextView>(R.id.due_Loan_amount)
        val takeLoan: TextView = itemView.findViewById<TextView>(R.id.takeLoan)


        val balanceNull: ShapeableImageView =
            itemView.findViewById<ShapeableImageView>(R.id.balance_null)
        val rechargeBtn = itemView.findViewById<ConstraintLayout>(R.id.rechargeBtn)
        val loanbtn = itemView.findViewById<ConstraintLayout>(R.id.loanbtn)
        val loanDuo = itemView.findViewById<ConstraintLayout>(R.id.duo_loanbtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rcvHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return rcvHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: rcvHolder, position: Int) {

        val userInfo = arrayList[position]

        holder.internetUnit.text = "MB"
        holder.minAmount.text = userInfo.min.toString()
        holder.minSec.text = "Min"
        holder.smsAmount.text = userInfo.sms.toString()

        //valid till
        holder.validBalance.text = Helpers.highlightBoldSubstring("Valid till 25 Jun, 2024")

        //handle basic details section
        val internetAmountinGB = (userInfo.internet / 1024.0)

        //handle balance section
        holder.crntBalance.text = Helpers.formatCurrencyBalance(userInfo.current_balance)


        //handle internet section
        Helpers.configureInternetDisplay(holder, internetAmountinGB)

        //handle Button
        Helpers.configureLoanButtons(holder, userInfo)


        //handle minute section
//        var minuteAmount = userInfo.min.toString()
//        holder.minAmount.text = Helpers.splitMinutesAndSeconds(minuteAmount)[0]
//        holder.minSec.text = "Min " + Helpers.splitMinutesAndSeconds(minuteAmount)[1]


        //handle sms section
        holder.smsAmount.text = userInfo.sms.toString()

        //handle recharge button
        if (userInfo.current_balance.toDouble() < 10.00)
            holder.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


    }
}