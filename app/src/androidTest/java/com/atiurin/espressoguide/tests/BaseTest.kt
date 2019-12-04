package com.atiurin.espressoguide.tests

import androidx.test.espresso.IdlingRegistry
import com.atiurin.espressoguide.framework.ScreenshotLifecycleListener
import com.atiurin.espressoguide.framework.getDefaultIdlingScope
import com.atiurin.espressoguide.idlingresources.idling
import com.atiurin.espressoguide.idlingresources.idlingContainer
import com.atiurin.espressopageobject.core.action.ViewActionLifecycle
import com.atiurin.espressopageobject.core.assertion.ViewAssertionLifecycle
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class  BaseTest {

    //attach screenshot to allure report in case of failure
    @get:Rule
    val failshot = FailshotRule()

    //attach logcat to allure report in case of failure
    @get:Rule
    val ruleChain = RuleChain.outerRule(LogcatClearRule()).around(LogcatDumpRule())

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            val listener = ScreenshotLifecycleListener()
//            ViewActionLifecycle.addListener(listener)
            ViewAssertionLifecycle.addListener(listener)
        }
    }

    @Before
    fun registerIdling() {
        idlingContainer.set(getDefaultIdlingScope())
        idling { IdlingRegistry.getInstance().register(contactsIdling) }
    }

    @After
    fun unregisterIdling() {
        idling { IdlingRegistry.getInstance().unregister(contactsIdling) }
    }
}