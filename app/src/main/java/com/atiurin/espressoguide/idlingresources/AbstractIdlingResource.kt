package com.atiurin.espressoguide.idlingresources

import androidx.annotation.Nullable
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractIdlingResource : AppIdlingResource {
    @Nullable
    @Volatile
    private var resourceCallback: AppIdlingResource.AppResourceCallback? = null
    private val idleNow = AtomicBoolean(true)
    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdle(): Boolean {
        return idleNow.get()
    }

    override fun registerIdleTransitionCallback(resourceCallback: AppIdlingResource.AppResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    private fun setIdleState(isIdleNow: Boolean) {
        idleNow.set(isIdleNow)
        if (isIdleNow && resourceCallback != null) {
            resourceCallback?.onTransitionToIdleState()
        }
    }

    fun onLoadStarted() = setIdleState(false)
    fun onDataLoaded() = setIdleState(true)
}