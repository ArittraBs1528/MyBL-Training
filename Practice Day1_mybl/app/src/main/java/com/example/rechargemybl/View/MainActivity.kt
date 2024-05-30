package com.example.rechargemybl.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rechargemybl.R
import com.example.rechargemybl.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val initialText = "Valid till 25 Jun, 2024";
        val currentBalance = "1400.00"
        binding.validText.text = makeValidateString(initialText)
//        binding.balance.text = formationHandle(currentBalance)


    }


    private fun makeValidateString(initialText: String): SpannableString {

        return SpannableString(initialText).apply {
            setSpan(
                StyleSpan(android.graphics.Typeface.BOLD),
                11,
                initialText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        }
    }


    @SuppressLint("DefaultLocale")
    private fun formationHandle(initialBalance: String): SpannableString {


        val convertedBalance = initialBalance.toDouble()
        val fomrattedBalance: String

        if (convertedBalance % 1.0 == 0.0) fomrattedBalance =
            String.format("%.0f", convertedBalance)
        else fomrattedBalance = String.format("%.2f", convertedBalance)


        val finalString = "৳ ${fomrattedBalance}"
        val spannableBalace = SpannableString(finalString).apply {
            setSpan(
                ForegroundColorSpan(resources.getColor(R.color.black)),
                2,
                initialBalance.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

            )

        }


        return spannableBalace
    }
}