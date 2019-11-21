package com.atiurin.espressoguide

import android.util.Log
import com.atiurin.espressoguide.tests.AdvancedEspressoTest
import com.atiurin.espressoguide.tests.DemoEspressoTest
import com.atiurin.espressoguide.tests.SimpleEspressoTest
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig
import io.qameta.allure.espresso.deviceScreenshot
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AdvancedEspressoTest::class,
    SimpleEspressoTest::class,
    DemoEspressoTest::class)
class TestSuite