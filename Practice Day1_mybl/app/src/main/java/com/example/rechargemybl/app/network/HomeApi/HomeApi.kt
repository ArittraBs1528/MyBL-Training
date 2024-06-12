package com.example.rechargemybl.app.network.HomeApi

import com.example.rechargemybl.app.model.apiModel.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("949326c71065be4436c1")
    suspend fun getHomeInfo(): Response<BaseResponse>

}