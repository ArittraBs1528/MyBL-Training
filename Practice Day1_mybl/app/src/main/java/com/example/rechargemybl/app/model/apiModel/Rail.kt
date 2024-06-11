package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class Rail(
    @SerializedName("component_identifier") val componentIdentifier: String? = null,
    @SerializedName("deeplink") val deeplink: String? = null,
    @SerializedName("icon") val icon: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("is_highlight") val isHighlight: Boolean? = null,
    @SerializedName("title_bn") val titleBn: String? = null,
    @SerializedName("title_en") val titleEn: String? = null
)