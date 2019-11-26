package com.atiurin.espressoguide.idlingresources

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.atiurin.espressoguide.MyApplication

open class Holder<out T>(private val constructor: () -> T) {

    private var instance: T? = null

    @VisibleForTesting
    fun getInstanceFromTest(): T? {
        return when {
            instance != null -> instance
            else -> {
                instance = constructor()
                instance
            }
        }
    }

    fun getInstanceFromApp(): T? {
        return instance
    }
}