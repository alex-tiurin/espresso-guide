package com.atiurin.espressoguide.framework

import android.util.Log

fun step(description: String, action: () -> Unit){
    Log.d("Espresso step", description)
    ru.tinkoff.allure.step(description){
        action()
    }
}