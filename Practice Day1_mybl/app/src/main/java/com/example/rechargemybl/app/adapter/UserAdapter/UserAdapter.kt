package com.example.rechargemybl.app.adapter.UserAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.Utility.Helpers.TYPE_BALANCE
import com.example.rechargemybl.app.Utility.Helpers.TYPE_BILLS
import com.example.rechargemybl.app.Utility.Helpers.TYPE_PLAN_OFFER
import com.example.rechargemybl.app.Utility.Helpers.typeMap
import com.example.rechargemybl.app.adapter.ChildAdapter.PlanOfferItemViewMargin
import com.example.rechargemybl.app.adapter.ChildAdapter.PlanOfferAdapter
import com.example.rechargemybl.app.model.BillDao
import com.example.rechargemybl.app.model.PlanOfferDao
import com.example.rechargemybl.app.model.RvData
import com.example.rechargemybl.app.model.UserDao
import com.example.rechargemybl.databinding.BillsItemsViewBinding
import com.example.rechargemybl.databinding.ItemViewBinding
import com.example.rechargemybl.databinding.PlanandofferBinding
import java.util.Locale


class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<RvData>()

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        val type = dataSet[position].type
        return typeMap[type] ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Log.wtf("Aritra", "onCreateViewHolder: $viewType")

        val key = typeMap.filterValues { it == viewType }.keys.first()

        when (key) {
            TYPE_BALANCE -> {
                return UserViewHolder.create(parent)
            }

            TYPE_BILLS -> {
                return BillViewHolder.create(parent)
            }

            TYPE_PLAN_OFFER -> {
                return PlanOfferViewHolder.create(parent)
            }
        }

        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.wtf("aritra", "onBindViewHolder: $position")

        when (holder) {
            is UserViewHolder -> holder.bind(dataSet.getOrNull(position)?.userDao)
            is BillViewHolder -> holder.bind(dataSet.getOrNull(position)?.billDao)
            is PlanOfferViewHolder -> holder.bind(dataSet.getOrNull(position)?.planOffer)
        }
    }

    fun submitData(people: List<RvData>) {
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

    class UserViewHolder(private val viewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ItemViewBinding.inflate(inflater, parent, false)
                return UserViewHolder(view)
            }
        }


        fun bind(user: UserDao?) {

            viewBinding.balance.text = user?.current_balance

            viewBinding.validText.text = Helpers.highlightBoldSubstring("Valid till 25 Jun, 2024")

            //handle basic details section
            viewBinding.balance.text = user?.current_balance?.let {
                Helpers.formatCurrencyBalance(it)
            }


            //handle recharge button section
            if (user?.current_balance?.toDouble()!! < 10.00) viewBinding.rechargeBtn.setBackgroundResource(
                R.drawable.button_red_back
            )

            //handle loan button section
            configureLoanButtons(viewBinding, user)

            //handle internet section
            val userInternetInGB = (user.internet.div(1024.0))
            configureInternetDisplay(viewBinding, userInternetInGB)


            //handle minute section
            val minuteAmount = user.min.toString()
            val pair = Helpers.splitMinutesAndSeconds(minuteAmount);
            val minutes = pair.first
            val seconds = pair.second
            viewBinding.minuteAmount.text = minutes
            viewBinding.minSec.text = viewBinding.root.context.getString(R.string.Minutes, seconds)


            //handle sms section
            viewBinding.smsAmount.text = user.sms.toString()

        }


        private fun configureLoanButtons(viewBinding: ItemViewBinding, user: UserDao?) {
            if (user?.Loan_due != null) viewBinding.dueLoanAmount.text =
                viewBinding.root.context.getString(R.string.dueLoanAmount, user.Loan_due.toString())
            else if (user?.can_take_loan != null) {
                viewBinding.loanbtn.visibility = View.VISIBLE
                viewBinding.duoLoanbtn.visibility = View.GONE
                viewBinding.takeLoan.text = viewBinding.root.context.getString(
                    R.string.takeLoan, user.can_take_loan.toString()
                )
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
                viewBinding.balanceNull.visibility = View.VISIBLE
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


    class BillViewHolder(private val viewBinding: BillsItemsViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        companion object {
            fun create(parent: ViewGroup): BillViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = BillsItemsViewBinding.inflate(inflater, parent, false)
                return BillViewHolder(view)
            }
        }


        fun bind(billsDao: BillDao?) {

            if (billsDao != null) {
                billsDao.image?.let { viewBinding.cartInImage.setImageResource(it) }
                billsDao.sellAll?.let { viewBinding.seeAll.text = it }
                billsDao.sponsorName?.let { viewBinding.paystation.text = it }
                billsDao.bills?.let { viewBinding.bills.text = it }
                billsDao.poweredBy?.let {
                    viewBinding.soujonno.text =
                        viewBinding.root.context.getString(R.string.soujonno, it)
                }

            }

        }
    }

    class PlanOfferViewHolder(private val viewBinding: PlanandofferBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val marginLayout = PlanOfferItemViewMargin()
        val layoutManager = LinearLayoutManager(viewBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
        val planOfferAdapter = PlanOfferAdapter()

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

        fun bind(planOfferDao: ArrayList<PlanOfferDao>?) {
            if (planOfferDao != null) {
                planOfferAdapter.submitData(planOfferDao)
            }
        }
    }
}