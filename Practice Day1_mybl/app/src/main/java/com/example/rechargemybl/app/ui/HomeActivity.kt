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

        setUpUi()
        //loading data from api
        loadedDataFromApi()


        //balance section  - observe viewModel BalanceData
        viewModel.data.observe(this) { data ->
            updateData(data)
        }


        binding.swiperefresh.setOnRefreshListener {
//            count++;
            binding.swiperefresh.isRefreshing = false

//            if (count % 2 == 0)
//                updateData(createDemoUser2())
//            else
//                updateData(createDemoUser())
        }


    }

    private fun setUpUi() {
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
}

