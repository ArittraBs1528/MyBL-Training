package com.example.rechargemybl.app.model

data class RvData(
    var id: Int,
    val type: String,
    val userDao: UserDao?,
    val billDao: BillDao?,
    val planOffer: ArrayList<PlanOfferDao>?

)
