package com.atiurin.espressoguide

import com.atiurin.espressoguide.activity.MainActivity
import org.junit.Rule

class FriendsListTest{
    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
}