package com.example.rechargemybl.app.ui.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.model.apiModel.BalanceCard
import com.example.rechargemybl.app.model.apiModel.Loan
import com.example.rechargemybl.app.utility.orZero
import com.example.rechargemybl.databinding.ItemViewBinding
import java.util.Locale

class BalanceViewHolder(private val viewBinding: ItemViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        fun create(parent: ViewGroup): BalanceViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = ItemViewBinding.inflate(inflater, parent, false)
            return BalanceViewHolder(view)
        }
    }


    fun bind(accountBalance: BalanceCard?) {

        Log.wtf("TAG", "bindLheo: ${accountBalance}")

        //  --- handle left Tk Section
        val userBalanceData = accountBalance?.balance

        val formattedDate =
            itemView.context.getString(R.string.valid_till, Helpers.getBalanceTime(userBalanceData?.expiresIn.toString()))



        viewBinding.validText.text = Helpers.highlightBoldSubstring(formattedDate, 11)
            ?: itemView.context.getString(R.string.valid_till)

        userBalanceData?.amount?.let {
            viewBinding.balance.text = Helpers.formatCurrencyBalance(it)?:itemView.context.getString(R.string.balance)
        }


        //handle recharge button section
        val loan = userBalanceData?.loan
        if (userBalanceData?.amount != null && userBalanceData.amount > 10.00) {
            viewBinding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)
        }


        //handle loan button section
        if (loan != null) {
            configureLoanButtons(viewBinding, loan)
        }

        //  --- end handle left Tk Section ---- //


        // --- handle right portion --- //

        //handle internet section
        val internetBalance = accountBalance?.internet
        val internetAmountInGB = (internetBalance?.total?.div(1024.0))
        if (internetAmountInGB != null) {
            configureInternetDisplay(viewBinding, internetAmountInGB)
        }


        if(accountBalance!=null){
            if (accountBalance.internet?.remaining.orZero() < accountBalance.internet?.threshold.orZero() ) {
                viewBinding.internetBalanceNull.visibility = View.VISIBLE
            }
        }else
            viewBinding.internetBalanceNull.visibility = View.VISIBLE



        //handle minute section
        val minuteAmount = accountBalance?.minutes?.total.toString()
        viewBinding.minuteAmount.text = minuteAmount
        if (accountBalance != null) {
            viewBinding.minSec.text = accountBalance.minutes?.unit.toString()
        }
        if (accountBalance != null) {
            if (accountBalance.minutes?.remaining.orZero() < accountBalance.minutes?.threshold.orZero()) {
                viewBinding.minuteNull.visibility = View.VISIBLE
            }
        }



        //handle sms section
        if (accountBalance != null) {
            if (accountBalance.sms?.remaining.orZero() < accountBalance.minutes?.threshold.orZero()) {
                viewBinding.msgNull.visibility = View.VISIBLE
            }
        }
        val smsAmount = accountBalance?.sms
        if (smsAmount != null) {
            viewBinding.smsAmount.text = smsAmount.total.toString()
        }

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