package com.example.rechargemybl.app.utility

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Map<String, Any?>.toObject(): T {
    return convert()
}

inline fun <T, reified R> T.convert(): R {
    val json = Gson().toJson(this)
    return Gson().fromJson(json, object : TypeToken<R>() {}.type)
}

fun Double?.orZero(): Double = this ?: 0.0
fun Int?.orZero(): Int = this ?: 0

fun Int.addTwo() = this + 2