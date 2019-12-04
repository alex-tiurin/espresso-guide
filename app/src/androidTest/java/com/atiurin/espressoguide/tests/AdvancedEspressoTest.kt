package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressopageobject.recyclerview.withRecyclerView
import io.qameta.allure.android.step
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class AdvancedEspressoTest : BaseTest() {
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun backgroundLogin() {
        AccountManager(getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        mActivityRule.launchActivity(Intent())
    }

    /**
     *  clear espresso test. it is hard to be maintained
     *  Look at better approach [DemoEspressoTest]
     */
//    @Ignore
    @Test
    fun clearEspressoTestWithRecyclerViewActions() {
        val messageText = "message4"
        val itemMatcher = hasDescendant(allOf(withId(R.id.tv_name), withText("Janice")))
        onView(withId(R.id.recycler_friends))
            .perform(
                RecyclerViewActions
                    .actionOnItem<RecyclerView.ViewHolder>(itemMatcher, click())
            )

        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
        onView(withText("Clear history")).perform(click())
        onView(withId(R.id.message_input_text)).perform(typeText(messageText))
        onView(withId(R.id.send_button)).perform(click())
        onView(withId(R.id.messages_list))
            .perform(
                RecyclerViewActions
                    .scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(messageText)))
            )
        onView(
            allOf(
                withText(messageText),
                withId(R.id.message_text)
            )
        ).check(matches(isDisplayed()))
    }

    @Ignore
    @Test
    fun ignoredTest() {
        step("fail step") {
            onView(withRecyclerView(withId(R.id.recycler_friends)).atItem(hasDescendant(withText("Failed test")))).perform(
                click()
            )
        }
    }
}