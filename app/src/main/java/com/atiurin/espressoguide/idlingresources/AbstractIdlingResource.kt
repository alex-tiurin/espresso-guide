package com.atiurin.espressoguide.idlingresources

import androidx.annotation.Nullable
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean
import androidx.test.espresso.IdlingResource.ResourceCallback

abstract class AbstractIdlingResource : IdlingResource {
    @Nullable
    @Volatile
    private var resourceCallback: ResourceCallback? = null
    private val idleNow = AtomicBoolean(true)
    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return idleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        resourceCallback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        idleNow.set(isIdleNow)
        if (isIdleNow && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
    }
}