package com.atiurin.espressoguide.framework

import android.util.Log
import com.atiurin.espressopageobject.core.Description
import com.atiurin.espressopageobject.listeners.AbstractLifecycleListener
import io.qameta.allure.espresso.deviceScreenshot

class ScreenshotLifecycleListener : AbstractLifecycleListener() {
    override fun before(description: Description) {
        Log.d(
            "EspressoScreenshot",
            "Taking screenshot before execution of ${description.description}"
        )
        deviceScreenshot(description.type.toString())
    }

    /**
     * Will use allure [FailshotRule] instead of capturing screenshot here
     */
    override fun afterFailure(description: Description, throwable: Throwable) {
        //do nothing
    }
}