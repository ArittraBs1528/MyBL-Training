package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class RoamingBalance(
    val amount: Int?=null,
    val currency: String?=null,
    @SerializedName("expires_in")
    val expiresIn: Any?=null
)