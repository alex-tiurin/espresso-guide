package com.atiurin.espressoguide.tests

import android.util.Log
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import io.qameta.allure.espresso.deviceScreenshot
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.RuleChain

abstract class BaseTest {

    //attach screenshot to allure report in case of failure
    @get:Rule val failshot = FailshotRule()

    //attach logcat to allure report in case of failure
    @get:Rule val ruleChain = RuleChain.outerRule(LogcatClearRule()).around(LogcatDumpRule())

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass(){
            ViewActionsConfig.beforeAction = {
                Log.d("Espresso", "take screenshot before action")
                //attach screenshot to allure report and map it to current step
                deviceScreenshot("Action ${ViewActionsConfig.currentEspressoAction?.type}")
            }

            ViewAssertionsConfig.beforeAssertion = {
                Log.d("Espresso", "take screenshot before assertion")
                //attach screenshot to allure report and map it to current step
                deviceScreenshot("Assertion ${ViewAssertionsConfig.currentEspressoAssertion?.type}")
            }
        }
    }

}