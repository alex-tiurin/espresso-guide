package com.atiurin.espressoguide

import com.atiurin.espressoguide.tests.AdvancedEspressoTest
import com.atiurin.espressoguide.tests.SimpleEspressoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AdvancedEspressoTest::class,
    SimpleEspressoTest::class)
class TestSuite