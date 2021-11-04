package com.atiurin.espressoguide.framework.reporting

import android.util.Log
import io.qameta.allure.kotlin.Allure.step

inline fun <T> step (description: String, crossinline action: () -> T): T {
    Log.d("Espresso step", "------------ $description ------------ ")
    return step(description) {
        action()
    }
}