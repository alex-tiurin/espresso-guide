package com.atiurin.espressoguide.framework.idlingresource

import androidx.test.espresso.IdlingResource
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.idlingresources.AppIdlingResource

class BaseIdlingResource(private val appIdlingResource: AppIdlingResource) : IdlingResource, AppIdlingResource by appIdlingResource {
    override fun isIdleNow(): Boolean {
        return appIdlingResource.isIdle()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        Logger.debug("Idling. registerIdleTransitionCallback $name")
        appIdlingResource.registerIdleTransitionCallback(BaseResourceCallback(callback))
    }

    inner class BaseResourceCallback(private val callback: IdlingResource.ResourceCallback) : AppIdlingResource.AppResourceCallback, IdlingResource.ResourceCallback by callback {
        override fun onTransitionToIdleState() {
            Logger.debug("Idling. onTransitionToIdleState resource ${this@BaseIdlingResource.name}")
            callback.onTransitionToIdle()
        }
    }
}