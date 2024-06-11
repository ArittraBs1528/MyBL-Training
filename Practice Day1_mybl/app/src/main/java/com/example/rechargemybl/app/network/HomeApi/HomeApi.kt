package com.example.rechargemybl.app.network.HomeApi

import com.example.rechargemybl.app.model.apiModel.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("/2bb453b792f2d858b606")
    suspend fun getHomeInfo(): Response<BaseResponse>

}