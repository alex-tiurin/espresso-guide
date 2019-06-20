package com.atiurin.espressoguide.idlingresources

import android.content.Context
import com.atiurin.espressoguide.MyApplication

open class SingletonHolder<out T>(private val constructor: () -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(context: Context): T? {
        return when {
            instance != null -> instance!!
            else -> synchronized(this) {
                if (context == MyApplication.context)
                    return null
                if (instance == null) instance = constructor()
                instance!!
            }
        }
    }

    fun getInstanceFromTest(): T? {
        return when {
            instance != null -> instance!!
            else -> synchronized(this) {
                if (instance == null) instance = constructor()
                instance!!
            }
        }
    }

    fun getInstanceFromApp(): T? {
        return synchronized(this) {
            if (instance == null) instance = constructor()
            instance!!
        }
    }
}