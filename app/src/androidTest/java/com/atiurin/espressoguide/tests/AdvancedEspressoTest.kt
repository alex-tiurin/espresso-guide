package com.atiurin.espressoguide.tests

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressoguide.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressopageobject.extensions.*
import com.atiurin.espressopageobject.recyclerview.withRecyclerView
import io.qameta.allure.android.step
import io.qameta.allure.espresso.FailshotRule
import org.hamcrest.Matchers.allOf
import org.junit.*

class AdvancedEspressoTest : BaseTest() {
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()
    private val idlingRes2 = ChatIdlingResource.getInstanceFromTest()
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun registerResource() {
        AccountManager(getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(idlingRes, idlingRes2)
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
        Thread.sleep(1000)
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

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes, idlingRes2)
    }


}