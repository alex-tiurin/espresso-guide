package com.atiurin.espressoguide.framework.reporting

import android.util.Log
import com.atiurin.espressoguide.framework.Page

inline fun <T> step (description: String, action: () -> T): T {
    Log.d("Espresso step", "------------ $description ------------ ")
    return io.qameta.allure.android.step(description) {
        action()
    }
}