package com.example.rechargemybl.app.ui

import android.os.Bundle
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
import com.example.rechargemybl.app.ui.adapter.mainAdapter.UserItemViewMargin
import com.example.rechargemybl.app.ui.adapter.mainAdapter.UserAdapter
import com.example.rechargemybl.app.model.apiModel.Data

import com.example.rechargemybl.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
    private val userAdapter = UserAdapter()
    private val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    private val itemDecoration = UserItemViewMargin()

    private var count = 0;

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

        setUpAdapterUi()

        loadedDataFromApi()

        setSwipeRefreshColors()

        showSwipeRefreshIndicator()


        //balance section  - observe viewModel BalanceData
        viewModel.data.observe(this) { data ->
            handleDataRefreshResult(data)
        }


        //swipe refresh
        binding.swiperefresh.setOnRefreshListener {
            triggerDataRefresh()
        }
    }

    private fun setUpAdapterUi() {
        binding.peopleList.layoutManager = layoutManager
        binding.peopleList.addItemDecoration(itemDecoration)
        binding.peopleList.adapter = userAdapter
    }

    private fun loadedDataFromApi() {
        viewModel.getAllHomeData()
    }


    private fun updateData(list: List<Data>) {
        userAdapter.submitData(list)
    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun setSwipeRefreshColors() {
        binding.swiperefresh.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.orange
            )
        )
    }

    private fun showSwipeRefreshIndicator() {
        binding.swiperefresh.isRefreshing = true
    }

    private fun handleDataRefreshResult(data: List<Data>) { // Replace YourDataType with the actual type
        if (data.isNotEmpty()) {
            binding.swiperefresh.isRefreshing = false
            updateData(data)
        } else {
            binding.swiperefresh.isRefreshing = true
            // You might also want to display an error message or retry option here
        }
    }

    private fun triggerDataRefresh() {
        binding.swiperefresh.isRefreshing = false
        count++
        if (count % 2 == 0) {
            viewModel.getAllHomeData()
        } else {
            viewModel.getAllHomeData2()
        }
    }


}

