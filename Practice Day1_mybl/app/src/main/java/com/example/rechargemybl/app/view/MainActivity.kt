package com.example.rechargemybl.app.view

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rechargemybl.R
import com.example.rechargemybl.app.Utility.Helpers
import com.example.rechargemybl.app.adapter.UserAdapter.UserItemViewMargin
import com.example.rechargemybl.app.adapter.UserAdapter.UserAdapter
import com.example.rechargemybl.app.model.BillDao
import com.example.rechargemybl.app.model.PlanOfferDao
import com.example.rechargemybl.app.model.RvData

import com.example.rechargemybl.app.model.UserDao

import com.example.rechargemybl.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userAdapter = UserAdapter()
    private val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    private val itemDecoration = UserItemViewMargin()
    private var count = 1

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
        setStatusBarColor(ContextCompat.getColor(this, R.color.orange))

        displayUserRechargeSection(
            UserDao(
                2,
                "4.68", null,
                90,
                930.45, 14500.00,
                90, null
            )
        );

        prepareUserListView()
        binding.swiperefresh.setOnRefreshListener {
            count++;
            binding.swiperefresh.isRefreshing = false

            if (count % 2 == 0)
                updateData(createDemoUser2())
            else
                updateData(createDemoUser())
        }

    }


    private fun prepareUserListView() {
        binding.peopleList.layoutManager = layoutManager
        binding.peopleList.addItemDecoration(itemDecoration)
        binding.peopleList.adapter = userAdapter
        updateData(createDemoUser())
    }

    private fun updateData(list: ArrayList<RvData>) {
        userAdapter.submitData(list)
    }


    private fun displayUserRechargeSection(user: UserDao) {

        //handle valid text
        val initialText = "Valid till 25 Jun, 2024"


        //handle basic details section
        val internetAmountInGB = (user.internet / 1024.0)
        binding.validText.text = Helpers.highlightBoldSubstring(initialText, 11)
        binding.balance.text = Helpers.formatCurrencyBalance(user.current_balance)


        //handle recharge button section
        if (user.current_balance.toDouble() < 10.00)
            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


        //handle loan button section
        configureLoanButtons(binding, user)


        //handle internet section
        configureInternetDisplay(binding, internetAmountInGB)


        //handle minute section
        val minuteAmount = user.min.toString()
        val (minutes, seconds) = Helpers.splitMinutesAndSeconds(minuteAmount);
        binding.minuteAmount.text = minutes
        binding.minSec.text = getString(R.string.Minutes, seconds)


        //handle sms section
        binding.smsAmount.text = user.sms.toString()


    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }


    private fun createDemoUser(): ArrayList<RvData> {
        val RvInfo = ArrayList<RvData>()
        val Listadd = ArrayList<PlanOfferDao>()
        Listadd.add(PlanOfferDao(1,"list"))
        Listadd.add(PlanOfferDao(2,"list2"))
        Listadd.add(PlanOfferDao(3,"list3"))
        Listadd.add(PlanOfferDao(4,"list3"))
        Listadd.add(PlanOfferDao(5,"list3"))
        Listadd.add(PlanOfferDao(6,"list3 fd"))
        Listadd.add(PlanOfferDao(7,"list3 fdfg"))
        Listadd.add(PlanOfferDao(8,"list3"))
        Listadd.add(PlanOfferDao(9,"list3"))
        Listadd.add(PlanOfferDao(10,"list3d gfsf"))
        Listadd.add(PlanOfferDao(11,"list3 dsfsd fd"))

        RvInfo.add(
            RvData(
                1,
                "TYPE_PLAN_OFFER", null,
                null, Listadd
            )
        )

        RvInfo.add(
            RvData(
                2,
                "TYPE_USER",
                UserDao(
                    1,
                    "1400.0", 40,
                    null,
                    930.45, 0.00,
                    50, null
                ),
                null, null
            )
        )
        RvInfo.add(
            RvData(
                3,
                "TYPE_USER",
                UserDao(
                    3,
                    "1157.658", null,
                    null,
                    930.45, 500.00,
                    90, null
                ),
                null, null
            )
        )


        RvInfo.add(
            RvData(
                4,
                "TYPE_USER", UserDao(
                    2,
                    "4.68", null,
                    90,
                    930.45, 14500.00,
                    90, null
                ), null, null
            )
        )
        RvInfo.add(
            RvData(
                5,
                "TYPE_BILLS",
                null,
                BillDao(R.drawable.img1, "বিলস", "সব দেখুন", "সৌজন্যেঃ", " PayStation"), null
            )
        )
        RvInfo.add(
            RvData(
                6,
                "TYPE_BILLS", null,
                BillDao(R.drawable.img3, "বিলস", "সব দেখুন", "সৌজন্যেঃ", " PayStation"), null
            )
        )






        return RvInfo
    }


    private fun configureLoanButtons(viewBinding: ActivityMainBinding, user: UserDao?) {

        if (user?.Loan_due != null) viewBinding.dueLoanAmount.text =
            getString(R.string.dueLoanAmount, user.Loan_due.toString())
        else if (user?.can_take_loan != null) {
            viewBinding.loanbtn.visibility = View.VISIBLE
            viewBinding.duoLoanbtn.visibility = View.GONE
            viewBinding.takeLoan.text = getString(R.string.takeLoan, user.can_take_loan.toString())
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

            val convertedToMB = userInternetInGB * 1024.00

            val formattedUsage = when {
                convertedToMB % 1.0 == 0.0 -> String.format(
                    Locale.getDefault(),
                    "%.0f",
                    convertedToMB
                )

                else -> String.format(Locale.getDefault(), "%.2f", convertedToMB)
            }

            viewBinding.internetAmount.text = getString(R.string.internetAmount, formattedUsage)
            viewBinding.internetUnit.text = getString(R.string.internetUnitMB)

        } else {
            val formattedUsage = when {
                userInternetInGB % 1.0 == 0.0 -> String.format(
                    Locale.getDefault(),
                    "%.0f",
                    userInternetInGB
                )

                else -> String.format(Locale.getDefault(), "%.2f", userInternetInGB)
            }
            viewBinding.internetAmount.text = getString(R.string.internetAmount, formattedUsage)
            viewBinding.internetUnit.text = getString(R.string.internetUnitGB)
        }
    }


    private fun createDemoUser2(): ArrayList<RvData> {
        val RvInfo = ArrayList<RvData>()
        RvInfo.add(
            RvData(
                1,
                "TYPE_USER",
                UserDao(
                    1,
                    "1400.0", 40,
                    null,
                    930.45, 0.00,
                    50, null
                ),
                null, null
            )
        )
        RvInfo.add(
            RvData(
                2,
                "TYPE_USER",
                UserDao(
                    3,
                    "1157.658", null,
                    null,
                    930.45, 500.00,
                    90, null
                ),
                null, null
            )
        )


        RvInfo.add(
            RvData(
                3,
                "TYPE_USER", UserDao(
                    2,
                    "4.68", null,
                    90,
                    930.45, 14500.00,
                    90, null
                ), null, null
            )
        )


        return RvInfo
    }

}