package com.atiurin.espressoguide

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AdvancedEspressoTest::class,
    SimpleEspressoTest::class)
class TestSuite