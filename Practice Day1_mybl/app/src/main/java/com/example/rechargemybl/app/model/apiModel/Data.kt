package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Data(
    val banner: String,
    @SerializedName("component_key") val componentKey: String,
    val cta: Cta,
    @SerializedName("data") val accountBalance: AccountBalance,
    val icon: String,
    val id: Int,
    @SerializedName("is_eligible") val isEligible: Boolean,
    @SerializedName("is_title_show") val isTitleShow: Boolean,
    @SerializedName("other_component_id") val rails: List<Rail>,
    @SerializedName("title_bn") val titleBn: String,
    @SerializedName("title_en") val titleEn: String
)