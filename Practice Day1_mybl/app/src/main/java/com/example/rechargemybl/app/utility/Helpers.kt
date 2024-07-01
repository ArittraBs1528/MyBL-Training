package com.example.rechargemybl.app.Utility

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import java.text.SimpleDateFormat
import java.util.Locale

object Helpers {

    fun splitMinutesAndSeconds(minSec: String): Pair<String, String> {

        if (minSec.contains(".")) {
            val (minutes, seconds) = minSec.split(".")
            return Pair(minutes, seconds)
        }
        return Pair("0", "0")
    }

    fun highlightBoldSubstring(initialText: String, limit: Int): SpannableString {
        return SpannableString(initialText).apply {
            setSpan(

                StyleSpan(Typeface.BOLD),
                limit,
                initialText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }


    @SuppressLint("DefaultLocale")
    fun formatCurrencyBalance(initialBalance: String): SpannableString {

        val convertedBalance = initialBalance.toDouble()


        val formattedBalance = when {
            convertedBalance % 1.0 == 0.0 -> String.format("%.0f", convertedBalance)
            else -> String.format("%.2f", convertedBalance)
        }

        val finalString = "৳ $formattedBalance"

        return SpannableString(finalString).apply {
            setSpan(
                ForegroundColorSpan(Color.BLACK),
                2,
                finalString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    fun getBalanceTime(serverTime: String): String{
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = sdf.parse(serverTime)
            return SimpleDateFormat("d MMMM, yyyy", Locale.getDefault()).format(date)
        }catch (e: Exception){
            return ""
        }
    }

    const val TYPE_BALANCE = "balance_detail"
    const val TYPE_GENERIC_RAIL = "generic_rail"
    const val TYPE_AUDIOBOOK = "audiobook"
    const val TYPE_LIVE_RADIO = "live_radio"


    val typeMap = HashMap<String, Int>().apply {
        put(TYPE_BALANCE, 0)
        put(TYPE_GENERIC_RAIL, 1)
        put(TYPE_AUDIOBOOK, 2)
        put(TYPE_LIVE_RADIO, 3)

    }
}