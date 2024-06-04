package com.example.rechargemybl.app.view
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.adapter.UserAdapter

import com.example.rechargemybl.app.model.UserDao

import com.example.rechargemybl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user1: UserDao
    private lateinit var user2: UserDao
    private lateinit var user3: UserDao

    private val peopleAdapter = UserAdapter()

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

        preparePeopleListView()


        val userInfo = createUsers()

        displayUserReachargeSection(userInfo[1]);
//
//        binding.rcv1.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.rcv1.adapter = RcvAdapter(userInfo)
//
//        var dividerItemDecoration = DividerItemDecoration(this,RecyclerView.VERTICAL)
//        ResourcesCompat.getDrawable(resources,R.drawable.divider,null)?.let {
//            dividerItemDecoration.setDrawable(it)
//        }
//
//        binding.rcv1.addItemDecoration(dividerItemDecoration)

    }

    private fun preparePeopleListView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        val userInfo = createUsers()


        peopleAdapter.submitData(userInfo)

        binding.peopleList.layoutManager = layoutManager
        binding.peopleList.addItemDecoration(itemDecoration)
        binding.peopleList.adapter = peopleAdapter
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun displayUserReachargeSection(user: UserDao) {

        //handle valid text
        val initialText = "Valid till 25 Jun, 2024";


        //handle basic details section
        val internetAmountinGB = (user.internet / 1024.0)
        binding.validText.text = Helpers.highlightBoldSubstring(initialText)
        binding.balance.text = Helpers.formatCurrencyBalance(user.current_balance)


        //handle recharge button section
        if (user.current_balance.toDouble() < 10.00)
            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


        //handle loan button section
        Helpers.configureLoanButtons(binding,user)



        //handle internet section
        Helpers.configureInternetDisplay(binding, internetAmountinGB)




        //handle minute section
        var minuteAmount = user.min.toString()
        val pair = Helpers.splitMinutesAndSeconds(minuteAmount);
        val minutes = pair.first
        val seconds = pair.second
        binding.minuteAmount.text = minutes
        binding.minSec.text = "Min " + seconds



        //handle sms section
        binding.smsAmount.text = user.sms.toString()


    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun createUsers() : ArrayList<UserDao>{
        val userInfo = ArrayList<UserDao>()

        user1 = UserDao(1,
            "1400.0", 40,
            null,
            930.45, 0.00,
            50, null
        )
        user2 = UserDao(2,
            "4.68", null,
            90,
            930.45, 14500.00,
            90, null
        )

        user3 = UserDao(3,
            "1157.658", null,
            null,
            930.45, 500.00,
            90, null
        )

        userInfo.add(user1)
        userInfo.add(user2)
        userInfo.add(user3)

        return userInfo
    }
}