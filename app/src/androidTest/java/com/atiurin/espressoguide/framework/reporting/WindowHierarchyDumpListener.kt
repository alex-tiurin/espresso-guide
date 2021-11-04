package com.atiurin.espressoguide.framework.reporting

import androidx.test.uiautomator.UiDevice
import com.atiurin.ultron.core.common.Operation
import com.atiurin.ultron.core.common.OperationResult
import com.atiurin.ultron.listeners.UltronLifecycleListener
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class WindowHierarchyDumpListener(private val fileName: String = "window-hierarchy") :
    UltronLifecycleListener() {
    override fun afterFailure(operationResult: OperationResult<Operation>) {
        dumpHierarchy()
    }

    private fun dumpHierarchy() {
//        val attachmentFile = createAttachmentFile().apply {
//            this.writeBytes(uiDevice.dumpWindowHierarchy())
//        }
//        AllureAndroidLifecycle.addAttachment(
//            fileName, type = "text/xml", fileExtension = "xml", attachmentFile
//        )
    }

    private fun UiDevice.dumpWindowHierarchy(): ByteArray = ByteArrayOutputStream().apply {
        waitForIdle(TimeUnit.SECONDS.toMillis(10))
        dumpWindowHierarchy(this)
    }.toByteArray()
}


