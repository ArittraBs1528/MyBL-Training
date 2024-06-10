package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Balance(
    val amount: Double?=null,
    @SerializedName("expires_in")
    val expiresIn: String?=null,
    val loan: Loan?=null,
    val unit: String?=null
)