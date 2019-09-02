package com.atiurin.espressoguide.tests

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import org.junit.*

class UiTest{
    companion object {
        @BeforeClass @JvmStatic
        fun prepareOnce(){
            Log.d("Life>>", "BeforeClass")
            //do smth one time before activity launched
        }
        @AfterClass @JvmStatic
        fun finishOnce(){
            Log.d("Life>>", "AfterClass")
            //do smth after all tests finished
        }
    }

    @Rule @JvmField
    val mActivityRule =
        CustomActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        Log.d("Life>>", "Before")
        //do smth before activity launched
//        mActivityRule.launchActivity(Intent())
    }

    @After
    fun tearDown(){
        Log.d("Life>>", "After")
        //do smth before activity finished
    }

    @Test
    fun firstEspressoTest(){
        Log.d("Life>>", "Test start")
        val messageText = "test message"
        onView(withText("Chandler Bing")).perform(click())
        onView(withId(R.id.message_input_text)).perform(typeText(messageText))
        onView(withId(R.id.send_button)).perform(click())
        onView(withText(messageText)).check(matches(isDisplayed()))
        Log.d("Life>>", "Test finish")
    }
}