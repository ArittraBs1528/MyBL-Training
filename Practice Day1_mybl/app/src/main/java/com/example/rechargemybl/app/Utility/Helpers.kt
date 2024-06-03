package com.example.rechargemybl.app.Utility

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.example.rechargemybl.app.adapter.rcvAdapter
import com.example.rechargemybl.app.model.Balance
import com.example.rechargemybl.databinding.ActivityMainBinding

class Helpers {
    companion object {


        fun handleMinutes(minSec: String): List<String> {
            return minSec.split(".")
        }

        fun makeValidateString(initialText: String): SpannableString {
            return SpannableString(initialText).apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    11,
                    initialText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }


        @SuppressLint("DefaultLocale")
        fun formationHandle(initialBalance: String): SpannableString {

            val convertedBalance = initialBalance.toDouble()
            val fomrattedBalance: String

            if (convertedBalance % 1.0 == 0.0) fomrattedBalance =
                String.format("%.0f", convertedBalance)
            else fomrattedBalance = String.format("%.2f", convertedBalance)

            val finalString = "৳ $fomrattedBalance"

            return SpannableString(finalString).apply {
                setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    2,
                    finalString.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }


        fun handleInternet(binding: ActivityMainBinding, internetAmount: Double) {
            if (internetAmount == 0.00) {
                binding.balanceNull.visibility = View.VISIBLE
            } else if (internetAmount < 1.00) {
                binding.internetAmount.text =
                    (String.format("%.1f", internetAmount).toDouble() * 1000.00).toString()
                binding.internetUnit.text = "MB"
            } else {
                binding.internetAmount.text = String.format("%.2f", internetAmount)
                binding.internetUnit.text = "GB"
            }

        }


        fun handleInternet(holder: rcvAdapter.rcvHolder, internetAmount: Double) {
            if (internetAmount == 0.00) {
                holder.balanceNull.visibility = View.VISIBLE
            } else if (internetAmount < 1.00) {
                holder.internetAmount.text =
                    (String.format("%.1f", internetAmount).toDouble() * 1000.00).toString()
                holder.internetUnit.text = "MB"
            } else {
                holder.internetAmount.text = String.format("%.2f", internetAmount)
                holder.internetUnit.text = "GB"
            }

        }


        fun handleButtons(binding: ActivityMainBinding, user: Balance) {
            if (user.Loan_due != null) binding.dueLoanAmount.text =
                "Tk. " + user.Loan_due.toString()
//            binding.dueLoanAmount.text = getString(R.string.timeFormat, user.Loan_due)
            else if (user.can_take_loan != null) {
                binding.loanbtn.visibility = View.VISIBLE
                binding.duoLoanbtn.visibility = View.GONE
                binding.takeLoan.text = "Get ${user.can_take_loan} Tk Loan"
            } else {
                binding.loanbtn.visibility = View.GONE
                binding.duoLoanbtn.visibility = View.GONE
            }

        }

        fun handleButtons(binding: rcvAdapter.rcvHolder, user: Balance) {
            if (user.Loan_due != null) binding.due_Loan_amount.text =
                "Tk. " + user.Loan_due.toString()
//            binding.dueLoanAmount.text = getString(R.string.timeFormat, user.Loan_due)
            else if (user.can_take_loan != null) {
                binding.loanbtn.visibility = View.VISIBLE
                binding.loanDuo.visibility = View.GONE
                binding.takeLoan.text = "Get ${user.can_take_loan} Tk Loan"
            } else {
                binding.loanbtn.visibility = View.GONE
                binding.loanDuo.visibility = View.GONE
            }

        }

    }
}