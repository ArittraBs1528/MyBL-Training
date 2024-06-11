package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("data") val data: List<Data>?=null,
    @SerializedName("message") val message: String?=null,
    @SerializedName("status") val status: String?=null,
    @SerializedName("status_code") val statusCode: Int?=null
)