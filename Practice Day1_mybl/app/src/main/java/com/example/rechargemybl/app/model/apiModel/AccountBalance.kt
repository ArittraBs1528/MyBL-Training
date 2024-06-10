package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class AccountBalance(
    val sms: Sms?=null,
    val balance: Balance?=null,
    @SerializedName("pn_id")
    val pnId: String?=null,
    @SerializedName("connection_type")
    val connectionType: String?=null,
    val internet: Internet?=null,
    val minutes: Minutes?=null,
    @SerializedName("roaming_balance")
    val roamingBalance: RoamingBalance?=null,
    @SerializedName("validity_threshold")
    val validityThreshold: Int?=null
)