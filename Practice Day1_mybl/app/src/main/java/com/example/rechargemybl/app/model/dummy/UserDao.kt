package com.example.rechargemybl.app.model.dummy

data class UserDao(
    val id: Int? = 0,
    val currentBalance: String? = "0",
    val loanDue: Int? = 0,
    val canTakeLoan: Int? = 0,
    val min: Double? = 0.0,
    val internet: Double? = 0.0,
    val sms: Int? = 0,
    val date: String? = "0000-00-00"
)
