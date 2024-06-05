package com.example.rechargemybl.app.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
import com.example.rechargemybl.app.model.BillDao
import com.example.rechargemybl.app.model.RvData

import com.example.rechargemybl.app.model.UserDao

import com.example.rechargemybl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userAdapter = UserAdapter()

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

        val userInfo = createUsers()
        displayUserReachargeSection(userInfo[0]);

        prepareUserListView()


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


    private fun prepareUserListView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        val userInfo = createUsers()

        val userInfoPacked = createDemoUser()

        userAdapter.submitData(userInfoPacked)

        binding.peopleList.layoutManager = layoutManager
        binding.peopleList.addItemDecoration(itemDecoration)
        binding.peopleList.adapter = userAdapter
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
        configureLoanButtons(binding, user)


        //handle internet section
        configureInternetDisplay(binding, internetAmountinGB)


        //handle minute section
        var minuteAmount = user.min.toString()
        val (minutes, seconds) = Helpers.splitMinutesAndSeconds(minuteAmount);
        binding.minuteAmount.text = minutes
        binding.minSec.text = "Min $seconds"


        //handle sms section
        binding.smsAmount.text = user.sms.toString()


    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun createUsers(): ArrayList<UserDao> {
        val userInfo = ArrayList<UserDao>()
        userInfo.add(
            UserDao(
                1,
                "1400.0", 40,
                null,
                930.45, 0.00,
                50, null
            )
        )
        userInfo.add(
            UserDao(
                2,
                "4.68", null,
                90,
                930.45, 14500.00,
                90, null
            )
        )
        userInfo.add(
            UserDao(
                3,
                "1157.658", null,
                null,
                930.45, 500.00,
                90, null
            )
        )
        userInfo.add(
            UserDao(
                3,
                "1157.658", null,
                null,
                930.45, 500.00,
                90, null
            )
        )
        userInfo.add(
            UserDao(
                3,
                "1157.658", null,
                null,
                930.45, 500.00,
                90, null
            )
        )
        userInfo.add(
            UserDao(
                3,
                "1157.658", null,
                null,
                930.45, 500.00,
                90, null
            )
        )

        return userInfo
    }

    private fun createDemoUser(): ArrayList<RvData> {
        val RvInfo = ArrayList<RvData>()
        RvInfo.add(
            RvData(1,
                "TYPE_USER",   UserDao(
                    1,
                    "1400.0", 40,
                    null,
                    930.45, 0.00,
                    50, null
                ),null
            )
        )
        RvInfo.add(
            RvData(2,
                "TYPE_USER", UserDao(
                    3,
                    "1157.658", null,
                    null,
                    930.45, 500.00,
                    90, null
                ),null
            )
        )


        RvInfo.add(
            RvData(3,
                "TYPE_USER",  UserDao(
                    2,
                    "4.68", null,
                    90,
                    930.45, 14500.00,
                    90, null
                ),null
            )
        )
        RvInfo.add(
            RvData(3,
                "TYPE_BILLS",  null, BillDao(R.drawable.img1)
            )
        )
        RvInfo.add(
            RvData(3,
                "TYPE_BILLS",  null,BillDao(R.drawable.img3)
            )
        )


        return RvInfo
    }

    private fun configureLoanButtons(viewBinding: ActivityMainBinding, user: UserDao?) {
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

    private fun configureInternetDisplay(
        viewBinding: ActivityMainBinding,
        userInternetInGB: Double
    ) {
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