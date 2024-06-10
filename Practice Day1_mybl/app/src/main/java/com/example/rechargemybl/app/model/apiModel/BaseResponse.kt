package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    val data: List<Data>?=null,
    val message: String?=null,
    val status: String?=null,
    @SerializedName("status_code")
    val statusCode: Int?=null
)