package com.example.rechargemybl.app.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rechargemybl.app.model.apiModel.Balance
import com.example.rechargemybl.app.model.apiModel.BaseResponse
import com.example.rechargemybl.app.model.apiModel.Data
import com.example.rechargemybl.app.network.ApiClient
import com.example.rechargemybl.app.network.HomeApi.HomeApi
import com.example.rechargemybl.app.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private val _data: MutableLiveData<List<Data>> by lazy {
        MutableLiveData<List<Data>>()
    }

    val data: LiveData<List<Data>>
        get() = _data

    private val apiClient = ApiClient.getRetrofit().create(HomeApi::class.java)

    private val homeRepository = HomeRepository(apiClient)


    fun getAllHomeData() = viewModelScope.launch {
        _data.value = homeRepository.getHomeData()

//        Log.wtf("TAG", "getAllHomeData: ${response.body()}")
//        println("Our Data  ${response.body()}")

    }

}