package com.atiurin.espressoguide.framework

import android.util.Log

inline fun <reified T: Page> step(description: String, action: () -> Unit): T{
    Log.d("Espresso step", "------------ $description ------------ ")
    io.qameta.allure.android.step(description){
        action()
    }
    return T::class.java.newInstance()
}