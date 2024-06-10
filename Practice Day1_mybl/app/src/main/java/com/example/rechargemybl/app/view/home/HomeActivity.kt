package com.example.rechargemybl.app.view.home

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.rechargemybl.app.model.apiModel.AccountBalance
import com.example.rechargemybl.app.model.apiModel.Loan
import com.example.rechargemybl.app.model.dummy.BillDao
import com.example.rechargemybl.app.model.dummy.PlanOfferDao
import com.example.rechargemybl.app.model.dummy.RvData

import com.example.rechargemybl.app.model.dummy.UserDao

import com.example.rechargemybl.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
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


        //loading data from api
        loadedDataFromApi()


        //balance section  - observe viewModel BalanceData
        viewModel.data.observe(this) { data ->


            val accountBalance = data?.get(0)?.accountBalance

            if (accountBalance != null) {
                displayUserRechargeSection(accountBalance)
            }

        }



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

    private fun loadedDataFromApi() {
        viewModel.getAllHomeData()
    }

    private fun displayUserRechargeSection(accountBalance: AccountBalance) {


        //  --- handle left Tk Section
        val userBalanceData = accountBalance.balance

        val expireIn = userBalanceData?.expiresIn
        val pattern = """(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})"""
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = expireIn?.let { sdf.parse(it) }
        var formattedDate = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault()).format(date)
        formattedDate = "Valid till $formattedDate"


        binding.validText.text = Helpers.highlightBoldSubstring(formattedDate, 11)
        val balanceTk = userBalanceData?.amount.toString()
        binding.balance.text = Helpers.formatCurrencyBalance(balanceTk)


        //handle recharge button section
        val loan = userBalanceData?.loan
//        if (user.currentBalance!!.toDouble() < 10.00)
//            binding.rechargeBtn.setBackgroundResource(R.drawable.button_red_back)


        //handle loan button section
        if (loan != null) {
            configureLoanButtons(binding, loan)
        }

        //  --- end handle left Tk Section ---- //


        // --- handle right portion --- //

        //handle internet section
        val internetBalance = accountBalance.internet
        val internetAmountInGB = (internetBalance?.total?.div(1024.0))
        if (internetAmountInGB != null) {
            configureInternetDisplay(binding, internetAmountInGB)
        }


        //handle minute section
        val minuteAmount = accountBalance.minutes.toString()
        val (minutes, seconds) = Helpers.splitMinutesAndSeconds(minuteAmount);
        binding.minuteAmount.text = minutes
        binding.minSec.text = getString(R.string.Minutes, seconds)


        //handle sms section
        val smsAmount = accountBalance.sms
        binding.smsAmount.text = smsAmount?.total.toString()

        // --- end handle right portion --- //

    }


    private fun updateData(list: ArrayList<RvData>) {
        userAdapter.submitData(list)
    }

    private fun prepareUserListView() {
        binding.peopleList.layoutManager = layoutManager
        binding.peopleList.addItemDecoration(itemDecoration)
        binding.peopleList.adapter = userAdapter
        updateData(createDemoUser())
    }


    private fun createDemoUser(): ArrayList<RvData> {
        val RvInfo = ArrayList<RvData>()
        val Listadd = ArrayList<PlanOfferDao>()
        Listadd.add(PlanOfferDao(1, "list"))
        Listadd.add(PlanOfferDao(2, "list2"))
        Listadd.add(PlanOfferDao(3, "list3"))
        Listadd.add(PlanOfferDao(4, "list3"))
        Listadd.add(PlanOfferDao(5, "list3"))
        Listadd.add(PlanOfferDao(6, "list3 fd"))
        Listadd.add(PlanOfferDao(7, "list3 fdfg"))
        Listadd.add(PlanOfferDao(8, "list3"))
        Listadd.add(PlanOfferDao(9, "list3"))
        Listadd.add(PlanOfferDao(10, "list3d gfsf"))
        Listadd.add(PlanOfferDao(11, "list3 dsfsd fd"))

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


    private fun configureLoanButtons(viewBinding: ActivityMainBinding, loan: Loan) {


        if (loan.isEzligible == true) {
            viewBinding.loanbtn.visibility = View.VISIBLE
            viewBinding.duoLoanbtn.visibility = View.GONE
            viewBinding.takeLoan.text = getString(R.string.takeLoan, loan.amount.toString())
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

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }


}

