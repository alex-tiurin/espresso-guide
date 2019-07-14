package com.atiurin.espressoguide

import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(
    SimpleUnitTest::class,
    MockitoTest::class,
    RobolectricTest::class
)
@RunWith(Suite::class)
class UnitTestSuite