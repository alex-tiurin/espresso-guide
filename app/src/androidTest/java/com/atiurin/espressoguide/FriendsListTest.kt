package com.atiurin.espressoguide

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.idlingresources.ContactsIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FriendsListTest{
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java,false, false)

    @Before
    fun registerResource(){
        AccountManager(getInstrumentation().targetContext).login(CURRENT_USER.login, CURRENT_USER.password)
        val intent = Intent(getInstrumentation().targetContext, MainActivity::class.java)
        mActivityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(idlingRes)

    }

    @Test
    fun testRecyclerItem(){
        val item = FriendRecyclerItem(withId(R.id.recycler_friends), hasDescendant(allOf(withId(R.id.tv_title), withText("Janice"))))
        item.title.isDisplayed()
        item.status.hasText("Oh. My. God")
//        item.status.click()
//        onView(withRecyclerView(withId(R.id.recycler_friends)).atItem(hasDescendant(allOf(withId(R.id.tv_title), withText("Chandler Bing")))))
//            .check(matches(isDisplayed()))
//            .perform(click())
        Thread.sleep(2000)
//        onView(withText("Chandler Bing")).check(matches(isDisplayed()))
    }

    @Test
    fun testRecyclerItemChild(){
        onView(withId(R.id.recycler_friends))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(allOf(withId(R.id.tv_title), withText("Chandler Bing")))))
        onView(withRecyclerView(withId(R.id.recycler_friends))
                .atItem(hasDescendant(allOf(withId(R.id.tv_title), withText("Chandler Bing")))))
            .check(matches(isDisplayed()))
            .perform(click())
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
//        onView(withText("Clear history")).perform(click())
//        Thread.sleep(1000)
        onView(withId(R.id.message_input_text)).perform(typeText("message1"))
        onView(withId(R.id.send_button)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun testRecyclerItemChild2(){
        onView(withId(R.id.recycler_friends))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(allOf(withId(R.id.tv_title), withText("Chandler Bing")))))
        onView(withRecyclerView(withId(R.id.recycler_friends))
            .atItem(hasDescendant(allOf(withId(R.id.tv_title), withText("Chandler Bing")))))
            .check(matches(isDisplayed()))
            .perform(click())
        openActionBarOverflowOrOptionsMenu(getInstrumentation().context)
        onView(withText("Clear history")).perform(click())
        onView(withId(R.id.message_input_text)).perform(typeText("message1"))
        onView(withId(R.id.send_button)).perform(click())
        Thread.sleep(1000)
    }

    @After
    fun unregisterResource(){
        IdlingRegistry.getInstance().unregister(idlingRes)
    }

    class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>): RecyclerItem(list, item) {
        val title = getChildMatcher(withId(R.id.tv_title))
        val status = getChildMatcher(withId(R.id.tv_status))
    }
}