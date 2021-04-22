
package com.atiurin.espressoguide.framework.reporting

import com.atiurin.ultron.core.common.Operation
import com.atiurin.ultron.core.common.OperationResult
import com.atiurin.ultron.core.config.UltronConfig.UiAutomator.Companion.uiDevice
import com.atiurin.ultron.listeners.UltronLifecycleListener
import io.qameta.allure.espresso.AllureAndroidLifecycle
import io.qameta.allure.espresso.utils.createAttachmentFile

class ScreenshotLifecycleListener : UltronLifecycleListener() {
    override fun afterFailure(operationResult: OperationResult<Operation>) {
        takeScreenshot(operationResult.operation.name)
    }

    private fun takeScreenshot(name: String){
        val attachmentFile = createAttachmentFile()
        uiDevice.takeScreenshot(attachmentFile, 1f, 10)
        AllureAndroidLifecycle.addAttachment(name, "image/png", ".png", attachmentFile)
    }
}