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
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.adapter.rcvAdapter
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

        val userInfo = ArrayList<Balance>()

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
            "1157.658", null,
            null,
            930.45, 500.00,
            90, null
        )

        userInfo.add(user1)
        userInfo.add(user2)
        userInfo.add(user3)

        displayUserReachargeSection(user1);

        binding.rcv1.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcv1.adapter = rcvAdapter(userInfo)

        var dividerItemDecoration = DividerItemDecoration(this,RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources,R.drawable.divider,null)?.let {
            dividerItemDecoration.setDrawable(it)
        }


        binding.rcv1.addItemDecoration(dividerItemDecoration)


    }


    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun displayUserReachargeSection(user: Balance) {

        //handle valid text
        val initialText = "Valid till 25 Jun, 2024";


        //handle basic details section
        val internetAmountinGB = (user.internet / 1024.0)
        binding.validText.text = Helpers.makeValidateString(initialText)
        binding.balance.text = Helpers.formationHandle(user.current_balance)


        //handle recharge button section
        if (user.current_balance.toDouble() < 10.00)
            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


        //handle loan button section
        Helpers.handleButtons(binding,user)



        //handle internet section
        Helpers.handleInternet(binding, internetAmountinGB)



        //handle minute section
        var minuteAmount = user.min.toString()
        binding.minuteAmount.text = Helpers.handleMinutes(minuteAmount)[0]
        binding.minSec.text = "Min " + Helpers.handleMinutes(minuteAmount)[1]


        //handle sms section
        binding.smsAmount.text = user.sms.toString()


    }


    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}