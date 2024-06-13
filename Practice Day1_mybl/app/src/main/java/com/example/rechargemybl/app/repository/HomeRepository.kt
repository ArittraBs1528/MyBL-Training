package com.example.rechargemybl.app.repository

import com.example.rechargemybl.app.network.HomeApi.HomeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val homeApi: HomeApi) {

    suspend fun getHomeData() = withContext(Dispatchers.IO) {
        return@withContext homeApi.getHomeInfo().body()?.data //shorted
    }

    suspend fun getHomeData2() = withContext(Dispatchers.IO){
        return@withContext homeApi.getHomeInfo2().body()?.data //shorted
    }



}