package com.atiurin.espressoguide

import android.util.Log
import com.atiurin.espressoguide.tests.AdvancedEspressoTest
import com.atiurin.espressoguide.tests.DemoEspressoTest
import com.atiurin.espressoguide.tests.SimpleEspressoTest
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import ru.tinkoff.allure.android.deviceScreenshot

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AdvancedEspressoTest::class,
    SimpleEspressoTest::class,
    DemoEspressoTest::class)
class TestSuite{
    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass(){
            ViewActionsConfig.beforeAction = {
                Log.d("Espresso", "take screenshot before action")
                deviceScreenshot("Action ${ViewActionsConfig.currentEspressoAction?.type}")
            }

            ViewAssertionsConfig.beforeAssertion = {
                Log.d("Espresso", "take screenshot before assertion")
                deviceScreenshot("Assertion ${ViewAssertionsConfig.currentEspressoAssertion?.type}")
            }
        }
    }
}