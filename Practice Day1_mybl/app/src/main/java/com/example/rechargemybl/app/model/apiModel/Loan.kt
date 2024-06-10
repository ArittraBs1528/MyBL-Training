package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Loan(
    val amount: Int?=null,

    @SerializedName("is_ezligible")
    val isEzligible: Boolean?=null
)