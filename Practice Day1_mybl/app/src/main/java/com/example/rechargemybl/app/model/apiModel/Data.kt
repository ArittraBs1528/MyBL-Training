package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("banner") val banner: String,
    @SerializedName("component_key") val componentKey: String,
    @SerializedName("cta") val cta: Cta? = null,
    @SerializedName("data") val accountBalance: AccountBalance,
    @SerializedName("icon") val icon: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_eligible") val isEligible: Boolean,
    @SerializedName("is_title_show") val isTitleShow: Boolean,
    @SerializedName("rails") val rails: List<Rail>,
    @SerializedName("title_bn") val titleBn: String,
    @SerializedName("title_en") val titleEn: String,
    @SerializedName("lastThree") val lastThree: List<Data>? = null

)