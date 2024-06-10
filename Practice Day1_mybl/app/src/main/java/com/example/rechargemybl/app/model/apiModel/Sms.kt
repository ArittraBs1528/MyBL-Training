package com.example.rechargemybl.app.model.apiModel

data class Sms(
    val remaining: Int?=null,
    val threshold: Int?=null,
    val total: Int?=null,
    val unit: String?=null
)