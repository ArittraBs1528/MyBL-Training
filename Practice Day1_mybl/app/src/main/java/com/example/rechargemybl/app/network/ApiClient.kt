package com.example.rechargemybl.app.network

import com.example.rechargemybl.app.utility.HeaderConstraint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        fun getRetrofit(): Retrofit {

            return Retrofit.Builder().baseUrl(HeaderConstraint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }
    }


}