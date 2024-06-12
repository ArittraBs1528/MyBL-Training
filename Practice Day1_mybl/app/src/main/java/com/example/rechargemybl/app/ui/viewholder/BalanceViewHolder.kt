package com.example.rechargemybl.app.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.model.apiModel.AccountBalance
import com.example.rechargemybl.app.model.apiModel.Loan
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


    fun bind(accountBalance: AccountBalance) {

        //  --- handle left Tk Section
        val userBalanceData = accountBalance.balance

        val formattedDate = userBalanceData?.expiresIn?.let {
            "Valid till ${Helpers.getBalanceTime(it)}"
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