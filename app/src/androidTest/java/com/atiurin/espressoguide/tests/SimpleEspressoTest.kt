package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SimpleEspressoTest : BaseTest() {
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun registerResource() {
        //make login into app before test start and activity launched
        //to be sure that user is logged in when test start
        AccountManager(getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(idlingRes)

    }

    /**
     *   bad approach: chandler could be invisible (out of screen)
    in this case test will fail, look at better way in [AdvancedEspressoTest]
     */
    @Test
    fun simpleEspressoTest_SendStringMessage() {
        val messageText = "message1"
        onView(withText("Chandler Bing")).perform(click())
        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
        onView(withText("Clear history")).perform(click())
        onView(withId(R.id.message_input_text)).perform(typeText(messageText))
        onView(withId(R.id.send_button)).perform(click())
        onView(withText(messageText)).check(matches(isDisplayed()))
    }

    @Test
    fun simpleEspressoTest_SendNumbers() {
        val messageText = "1234567890"
        onView(withText("Rachel Green")).perform(click())
        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
        onView(withText("Clear history")).perform(click())
        onView(withId(R.id.message_input_text)).perform(typeText(messageText))
        onView(withId(R.id.send_button)).perform(click())
        onView(withText(messageText)).check(matches(isDisplayed()))
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes)
    }
}