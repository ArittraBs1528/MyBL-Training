package com.example.rechargemybl.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.model.UserDao
import com.example.rechargemybl.databinding.ItemViewBinding


class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet = ArrayList<UserDao>()

    override fun getItemCount(): Int {
        return dataSet.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind(dataSet.getOrNull(position))
        }
    }

    fun submitData(people: List<UserDao>) {
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

    class UserViewHolder(
        private val viewBinding: ItemViewBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

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
            if (user?.current_balance?.toDouble()!! < 10.00)
                viewBinding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)

            //handle loan button section
            configureLoanButtons(viewBinding, user)

            //handle internet section
            val userInternetInGB = (user.internet.div(1024.0))
            configureInternetDisplay(viewBinding, userInternetInGB)


            //handle minute section
            var minuteAmount = user.min.toString()
            val pair = Helpers.splitMinutesAndSeconds(minuteAmount);
            val minutes = pair.first
            val seconds = pair.second
            viewBinding.minuteAmount.text = minutes
            viewBinding.minSec.text = "Min " + seconds


            //handle sms section
            viewBinding.smsAmount.text = user.sms.toString()

        }


        private fun configureLoanButtons(viewBinding: ItemViewBinding, user: UserDao?) {
            if (user?.Loan_due != null) viewBinding.dueLoanAmount.text =
                "Tk. " + user.Loan_due.toString()
//            binding.dueLoanAmount.text = getString(R.string.timeFormat, user.Loan_due)
            else if (user?.can_take_loan != null) {
                viewBinding.loanbtn.visibility = View.VISIBLE
                viewBinding.duoLoanbtn.visibility = View.GONE
                viewBinding.takeLoan.text = "Get ${user.can_take_loan} Tk Loan"
            } else {
                viewBinding.loanbtn.visibility = View.GONE
                viewBinding.duoLoanbtn.visibility = View.GONE
            }

        }


        private fun configureInternetDisplay(viewBinding: ItemViewBinding, userInternetInGB: Double) {
            if (userInternetInGB == 0.00) {
                viewBinding.balanceNull.visibility = View.VISIBLE
            } else if (userInternetInGB < 1.00) {
                viewBinding.internetAmount.text =
                    (String.format("%.1f", userInternetInGB).toDouble() * 1000.00).toString()
                viewBinding.internetUnit.text = "MB"
            } else {
                viewBinding.internetAmount.text = String.format("%.2f", userInternetInGB)
                viewBinding.internetUnit.text = "GB"
            }
        }

    }
}
