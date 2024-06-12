package com.example.rechargemybl.app.model.apiModel

import com.google.gson.annotations.SerializedName

data class LoanAmount(
    @SerializedName("due_loan") var dueLoan: Int? = null,
    @SerializedName("expires_in") var expiresIn: String? = null
)
