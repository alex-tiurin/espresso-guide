
package com.atiurin.espressoguide.framework.reporting

import android.util.Log
import com.atiurin.ultron.core.common.Operation
import com.atiurin.ultron.core.common.OperationResult
import com.atiurin.ultron.listeners.AbstractLifecycleListener
import io.qameta.allure.espresso.deviceScreenshot

class ScreenshotLifecycleListener : AbstractLifecycleListener() {
    override fun before(operation: Operation) {
        Log.d(
            "EspressoScreenshot",
            "Taking screenshot before execution of ${operation.description}"
        )
        deviceScreenshot(operation.name)
    }

    /**
     * Will use allure [FailshotRule] instead of capturing screenshot here
     */
    override fun after(operationResult: OperationResult<Operation>) {
        super.after(operationResult)
    }
}