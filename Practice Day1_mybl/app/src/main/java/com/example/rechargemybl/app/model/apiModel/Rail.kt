package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Rail(
    @SerializedName("component_identifier")
    val componentIdentifier: String?=null,
    val deeplink: String?=null,
    val icon: String?=null,
    val id: Int?=null,
    @SerializedName("is_highlight")
    val isHighlight: Boolean?=null,
    @SerializedName("title_bn")
    val titleBn: String?=null,
    @SerializedName("title_en")
    val titleEn: String?=null
)