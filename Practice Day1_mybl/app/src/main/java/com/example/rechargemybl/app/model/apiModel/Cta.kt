package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Cta(
    @SerializedName("deeplzink") val deepLink: String? = null,
    @SerializedName("name_bn") val nameBn: String? = null,
    @SerializedName("name_en") val nameEn: String? = null
)