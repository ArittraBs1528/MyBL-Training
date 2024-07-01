package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Internet(
    @SerializedName("remaining") val remaining: Double? = null,
    @SerializedName("threshold") val threshold: Int? = null,
    @SerializedName("total") val total: Double? = null,
    @SerializedName("unit") val unit: String? = null
)