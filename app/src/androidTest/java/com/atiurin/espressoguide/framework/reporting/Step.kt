package com.atiurin.espressoguide.framework.reporting

import android.util.Log

fun step(description: String, action: () -> Unit){
    Log.d("Espresso step", "------------ $description ------------ ")
    io.qameta.allure.android.step(description){
        action()
    }
}