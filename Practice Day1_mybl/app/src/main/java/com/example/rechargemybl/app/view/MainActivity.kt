package com.example.rechargemybl.app.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rechargemybl.R
import com.example.rechargemybl.app.model.Balance
import com.example.rechargemybl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user1: Balance
    private lateinit var user2: Balance
    private lateinit var user3: Balance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setStatusBarColor(resources.getColor(R.color.orange))

        user1 = Balance(
            "1400.0", 40,
            null,
            930.45, 0.00,
            50, null
        )
        user2 = Balance(
            "4.68", null,
            90,
            930.45, 14500.00,
            90, null
        )

        user3 = Balance(
            "1157.65", null,
            null,
            930.45, 14500.00,
            90, null
        )


        displayUserReachargeSection(user3);

    }


    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun displayUserReachargeSection(user: Balance) {
        val initialText = "Valid till 25 Jun, 2024";
        val internetAmount = (user.internet / 1024.0)


        //handle basic details section
        binding.validText.text = makeValidateString(initialText)
        binding.balance.text = formationHandle(user.current_balance)


        //handle recharge button section
        if (user.current_balance.toDouble() < 10.00)
            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


        //handl loan button section
        if (user.Loan_due != null)
            binding.dueLoanAmount.text = "Tk. " + user.Loan_due.toString()
//            binding.dueLoanAmount.text = getString(R.string.timeFormat, user.Loan_due)
        else if (user.can_take_loan != null) {
            binding.loanbtn.visibility = View.VISIBLE
            binding.duoLoanbtn.visibility = View.GONE
            binding.takeLoan.text = "Get ${user.can_take_loan} Tk Loan"
        } else {
            binding.loanbtn.visibility = View.GONE
            binding.duoLoanbtn.visibility = View.GONE

        }


        //handle internet section
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


        //handle minute section
        var minuteAmount = user.min.toString()
        binding.minuteAmount.text = minuteAmount.split(".")[0]
        binding.minSec.text = "Min " + minuteAmount.split(".")[1]


        //handle sms section
        binding.smsAmount.text = user.sms.toString()


    }


    private fun makeValidateString(initialText: String): SpannableString {
        return SpannableString(initialText).apply {
            setSpan(
                StyleSpan(android.graphics.Typeface.BOLD),
                11,
                initialText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }


    @SuppressLint("DefaultLocale")
    private fun formationHandle(initialBalance: String): SpannableString {

        val convertedBalance = initialBalance.toDouble()
        val fomrattedBalance: String

        if (convertedBalance % 1.0 == 0.0) fomrattedBalance =
            String.format("%.0f", convertedBalance)
        else fomrattedBalance = String.format("%.2f", convertedBalance)

        val finalString = "৳ $fomrattedBalance"

//        println("final String" + finalString)
        return SpannableString(finalString).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                2,
                finalString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}