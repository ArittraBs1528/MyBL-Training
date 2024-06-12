package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banner") val banner: String?=null,
    @SerializedName("component_key") val componentKey: String?=null,
    @SerializedName("cta") val cta: Cta? = null,
    @SerializedName("data") val data: Any?=null,
    @SerializedName("icon") val icon: String?=null,
    @SerializedName("id") val id: Int?=null,
    @SerializedName("is_eligible") val isEligible: Boolean?=null,
    @SerializedName("is_title_show") val isTitleShow: Boolean?=null,
    @SerializedName("rails") val rails: List<Rail>?=null,
    @SerializedName("title_bn") val titleBn: String?=null,
    @SerializedName("title_en") val titleEn: String?=null
)