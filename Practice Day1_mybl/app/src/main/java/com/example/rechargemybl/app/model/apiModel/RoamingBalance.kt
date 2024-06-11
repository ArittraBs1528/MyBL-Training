package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class RoamingBalance(
    @SerializedName("amount") val amount: Int? = null,
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("expires_in") val expiresIn: Any? = null
)