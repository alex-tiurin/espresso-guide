package com.atiurin.espressoguide.tests

import androidx.test.espresso.IdlingRegistry
import com.atiurin.espressoguide.framework.reporting.ScreenshotLifecycleListener
import com.atiurin.espressoguide.framework.getDefaultIdlingScope
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.idlingresources.idling
import com.atiurin.espressoguide.idlingresources.idlingContainer
import com.atiurin.espressopageobject.core.espresso.action.ViewActionLifecycle
import com.atiurin.espressopageobject.core.espresso.assertion.ViewAssertionLifecycle
import com.atiurin.espressopageobject.testlifecycle.rulesequence.RuleSequence
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import org.junit.*

abstract class BaseTest {
    //attach screenshot to allure report in case of failure
    //attach logcat to allure report in case of failure
    @get:Rule
    val ruleSequence = RuleSequence(
        FailshotRule(),LogcatClearRule(), LogcatDumpRule(),
        SetUpTearDownRule()
            .addSetUp {
                Logger.life("SetUP in baseTest")
                idlingContainer.set(getDefaultIdlingScope())
                IdlingRegistry.getInstance().register(idlingContainer.get().contactsIdling)
            }.addTearDown {
                Logger.life("TearDown in baseTest")
                // you can get `contactsIdling` object in 2 ways
                idling { IdlingRegistry.getInstance().unregister(contactsIdling) }
            }
    )

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClassBase() {
            Logger.life("BeforeClass in baseTest")
            val listener = ScreenshotLifecycleListener()
            ViewActionLifecycle.addListener(listener)
            ViewAssertionLifecycle.addListener(listener)
        }

        @AfterClass
        @JvmStatic
        fun afterClassBase() {
            Logger.life("AfterClass in baseTest")
        }
    }
}