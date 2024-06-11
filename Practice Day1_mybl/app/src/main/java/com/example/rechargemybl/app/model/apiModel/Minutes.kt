package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Minutes(
    @SerializedName("remaining") val remaining: Int? = null,
    @SerializedName("threshold") val threshold: Int? = null,
    @SerializedName("total") val total: Int? = null,
    @SerializedName("unit") val unit: String? = null
)