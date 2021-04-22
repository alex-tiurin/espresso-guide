package com.atiurin.espressoguide.tests

import androidx.test.espresso.IdlingRegistry
import com.atiurin.espressoguide.framework.reporting.ScreenshotLifecycleListener
import com.atiurin.espressoguide.framework.getDefaultIdlingScope
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.framework.idlingresource.BaseIdlingResource
import com.atiurin.espressoguide.framework.reporting.WindowHierarchyDumpListener
import com.atiurin.espressoguide.idlingresources.idling
import com.atiurin.espressoguide.idlingresources.idlingContainer
import com.atiurin.ultron.core.config.UltronConfig
import com.atiurin.ultron.core.espresso.UltronEspressoOperationLifecycle
import com.atiurin.ultron.testlifecycle.rulesequence.RuleSequence
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.atiurin.ultron.testlifecycle.setupteardown.TearDownRule
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import io.qameta.allure.espresso.WindowHierarchyRule
import org.junit.*

abstract class BaseTest {
    //attach screenshot to allure report in case of failure
    //attach logcat to allure report in case of failure
    @get:Rule
    val ruleSequence = RuleSequence(
        LogcatClearRule(), LogcatDumpRule(),
        SetUpRule().add {
            UltronConfig.Espresso.ESPRESSO_OPERATION_POLLING_TIMEOUT = 0L
            idlingContainer.set(getDefaultIdlingScope())
            idling { IdlingRegistry.getInstance().register(BaseIdlingResource(contactsIdling)) }
        },
        TearDownRule().add {
            idling { IdlingRegistry.getInstance().unregister(BaseIdlingResource(contactsIdling)) }
        }
    )

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClassBase() {
            UltronConfig.addGlobalListener(ScreenshotLifecycleListener())
            UltronConfig.addGlobalListener(WindowHierarchyDumpListener())
        }
    }
}