package com.atiurin.espressoguide.framework

import android.util.Log

fun step(description: String, action: () -> Unit){
    ru.tinkoff.allure.step(description){
        Log.d("Espresso step", description)
        action()
    }
}