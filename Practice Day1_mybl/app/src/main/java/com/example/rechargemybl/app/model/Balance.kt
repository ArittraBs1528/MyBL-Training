package com.example.rechargemybl.app.model

data class Balance(
    val current_balance: String,
    val Loan_due: Int?,
    val can_take_loan: Int?,
    val min: Double,
    val internet: Double,
    val sms: Int,
    val date: String?
)
