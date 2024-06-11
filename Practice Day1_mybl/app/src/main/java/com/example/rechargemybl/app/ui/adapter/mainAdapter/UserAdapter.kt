package com.example.rechargemybl.app.ui.adapter.mainAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.Utility.Helpers.TYPE_BALANCE
import com.example.rechargemybl.app.Utility.Helpers.TYPE_AUDIOBOOK
import com.example.rechargemybl.app.Utility.Helpers.TYPE_LIVE_RADIO
import com.example.rechargemybl.app.Utility.Helpers.TYPE_GENERIC_RAIL
import com.example.rechargemybl.app.Utility.Helpers.getBalanceTime
import com.example.rechargemybl.app.Utility.Helpers.typeMap
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.PlanOfferItemViewMargin
import com.example.rechargemybl.app.ui.adapter.ChildAdapter.planOffer.PlanOfferAdapter
import com.example.rechargemybl.app.model.apiModel.AccountBalance
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.app.model.apiModel.Loan
import com.example.rechargemybl.app.model.apiModel.Rail

import com.example.rechargemybl.databinding.ItemViewBinding
import com.example.rechargemybl.databinding.ItemsViewBillsBinding
import com.example.rechargemybl.databinding.PlanandofferBinding
import java.text.SimpleDateFormat
import java.util.Locale


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
        }

        return BalanceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.wtf("aritra", "onBindViewHolder: $position")

        when (holder) {
            is BalanceViewHolder -> dataSet.getOrNull(position)?.accountBalance?.let {
                holder.bind(
                    it
                )
            }

            is AudioBookViewHolder -> dataSet.getOrNull(position)?.let { holder.bind(it) }
            is LiveRadioViewHolder -> dataSet.getOrNull(position)?.let { holder.bind(it) }
            is PlanOfferViewHolder -> holder.bind(dataSet.getOrNull(position)?.rails)
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

    class BalanceViewHolder(private val viewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): BalanceViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemViewBinding.inflate(inflater, parent, false)
                return BalanceViewHolder(view)
            }
        }


        fun bind(accountBalance: AccountBalance) {

            //  --- handle left Tk Section
            val userBalanceData = accountBalance.balance

            val formattedDate = userBalanceData?.expiresIn?.let {
                "Valid till ${getBalanceTime(it)}"
            } ?: ""

            viewBinding.validText.text = Helpers.highlightBoldSubstring(formattedDate, 11)
            val balanceTk = userBalanceData?.amount.toString()
            viewBinding.balance.text = Helpers.formatCurrencyBalance(balanceTk)


            //handle recharge button section
            val loan = userBalanceData?.loan
//        if (user.currentBalance!!.toDouble() < 10.00)
//            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


            //handle loan button section
            if (loan != null) {
                configureLoanButtons(viewBinding, loan)
            }

            //  --- end handle left Tk Section ---- //


            // --- handle right portion --- //

            //handle internet section
            val internetBalance = accountBalance.internet
            val internetAmountInGB = (internetBalance?.total?.div(1024.0))
            if (internetAmountInGB != null) {
                configureInternetDisplay(viewBinding, internetAmountInGB)
            }
            if (accountBalance.internet?.remaining!! < accountBalance.internet.threshold!!) {
                viewBinding.internetBalanceNull.visibility = View.VISIBLE
            }


            //handle minute section
            val minuteAmount = accountBalance.minutes?.total.toString()
            viewBinding.minuteAmount.text = minuteAmount
            viewBinding.minSec.text = accountBalance.minutes?.unit.toString()
            if (accountBalance.minutes?.remaining!! < accountBalance.minutes.threshold!!) {
                viewBinding.minuteNull.visibility = View.VISIBLE
            }


            //handle sms section
            if (accountBalance.sms?.remaining!! < accountBalance.minutes.threshold) {
                viewBinding.msgNull.visibility = View.VISIBLE
            }
            val smsAmount = accountBalance.sms
            viewBinding.smsAmount.text = smsAmount.total.toString()

            // --- end handle right portion --- //

        }


        private fun configureLoanButtons(viewBinding: ItemViewBinding, loan: Loan) {
            if (loan.isEzligible == true) {
                viewBinding.loanbtn.visibility = View.VISIBLE
                viewBinding.duoLoanbtn.visibility = View.GONE
                viewBinding.takeLoan.text =
                    viewBinding.root.context.getString(R.string.takeLoan, loan.amount.toString())
            } else {
                viewBinding.loanbtn.visibility = View.GONE
                viewBinding.duoLoanbtn.visibility = View.GONE
            }

        }


        private fun configureInternetDisplay(
            viewBinding: ItemViewBinding,
            userInternetInGB: Double
        ) {
            if (userInternetInGB == 0.00) {
                viewBinding.internetBalanceNull.visibility = View.VISIBLE
            } else if (userInternetInGB < 1.00) {
                val convertedToMB = userInternetInGB * 1024.00

                val formattedUsage = when {
                    convertedToMB % 1.0 == 0.0 -> String.format(
                        Locale.getDefault(), "%.0f", convertedToMB
                    )

                    else -> String.format(Locale.getDefault(), "%.2f", convertedToMB)
                }

                viewBinding.internetAmount.text =
                    viewBinding.root.context.getString(R.string.internetAmount, formattedUsage)
                viewBinding.internetUnit.text =
                    viewBinding.root.context.getString(R.string.internetUnitMB)
            } else {
                val formattedUsage = when {
                    userInternetInGB % 1.0 == 0.0 -> String.format(
                        Locale.getDefault(),
                        "%.0f",
                        userInternetInGB
                    )

                    else -> String.format(Locale.getDefault(), "%.2f", userInternetInGB)
                }
                viewBinding.internetAmount.text =
                    viewBinding.root.context.getString(R.string.internetAmount, formattedUsage)
                viewBinding.internetUnit.text =
                    viewBinding.root.context.getString(R.string.internetUnitGB)
            }
        }

    }


    class AudioBookViewHolder(private val viewBinding: ItemsViewBillsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): AudioBookViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemsViewBillsBinding.inflate(inflater, parent, false)
                return AudioBookViewHolder(view)
            }
        }


        fun bind(bills: Data) {

            Glide.with(viewBinding.cartInImage.context)
                .load(bills.banner)
                .into(viewBinding.cartInImage)



            viewBinding.seeAll.text = bills.cta?.nameBn
            if (bills.isTitleShow) {
                viewBinding.titles.text = bills.titleBn
                Glide.with(viewBinding.icon.context)
                    .load(bills.icon).into(viewBinding.icon)
            }


            if (bills.cta == null) {
                viewBinding.grp.visibility = View.GONE
            }


        }

    }


    class LiveRadioViewHolder(private val viewBinding: ItemsViewBillsBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): AudioBookViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemsViewBillsBinding.inflate(inflater, parent, false)
                return AudioBookViewHolder(view)
            }
        }


        fun bind(bills: Data) {

            Glide.with(viewBinding.cartInImage.context)
                .load(bills.banner)
                .into(viewBinding.cartInImage)

            Glide.with(viewBinding.icon.context)
                .load(bills.icon).into(viewBinding.icon)

            viewBinding.seeAll.text = bills.cta?.nameBn
            viewBinding.titles.text = bills.titleBn

            if (bills.isTitleShow) {
                viewBinding.titles.visibility = View.VISIBLE
                viewBinding.icon.visibility = View.VISIBLE
            } else {
                viewBinding.titles.visibility = View.GONE
                viewBinding.icon.visibility = View.GONE
            }


            viewBinding.seeAll.text = bills.cta?.nameEn

            if (bills.cta == null) {
                viewBinding.grp.visibility = View.GONE
            } else {
                viewBinding.grp.visibility = View.VISIBLE
            }
        }

    }


}

class PlanOfferViewHolder(private val viewBinding: PlanandofferBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    private val marginLayout = PlanOfferItemViewMargin()
    private val layoutManager =
        LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
    private val planOfferAdapter = PlanOfferAdapter()

    companion object {
        fun create(parent: ViewGroup): PlanOfferViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = PlanandofferBinding.inflate(inflater, parent, false)
            return PlanOfferViewHolder(view)
        }
    }

    init {
        viewBinding.planRcv.layoutManager = layoutManager
        viewBinding.planRcv.addItemDecoration(marginLayout)
        viewBinding.planRcv.adapter = planOfferAdapter
    }

    fun bind(planOfferList: List<Rail>?) {


        if (planOfferList != null) {
            planOfferAdapter.submitData(planOfferList)
        }
    }
}
