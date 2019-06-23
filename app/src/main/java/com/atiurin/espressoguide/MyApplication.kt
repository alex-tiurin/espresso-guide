package com.atiurin.espressoguide

import android.app.Application
import android.content.Context

object MyApplication : Application() {
    var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}