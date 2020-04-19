package com.atiurin.espressoguide

import android.util.Log

object Logger {
    private val LOG_TAG = "EspressoGuide"
    fun info(message: String) = Log.i(LOG_TAG, message)
    fun debug(message: String) = Log.d(LOG_TAG, message)
    fun error(message: String) = Log.e(LOG_TAG, message)
    fun warning(message: String) = Log.w(LOG_TAG, message)
    fun life(message: String) = Log.d("Life>>", message)
}