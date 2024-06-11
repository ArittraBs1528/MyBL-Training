package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Balance(
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("expires_in") val expiresIn: String? = null,
    @SerializedName("loan") val loan: Loan? = null,
    @SerializedName("unit") val unit: String? = null
)