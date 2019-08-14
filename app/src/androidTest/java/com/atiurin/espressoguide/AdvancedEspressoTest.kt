package com.atiurin.espressoguide

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.framework.*
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressoguide.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AdvancedEspressoTest {
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()
    private val idlingRes2 = ChatIdlingResource.getInstanceFromTest()
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)



    @Before
    fun registerResource() {
        AccountManager(getInstrumentation().targetContext).login(CURRENT_USER.login, CURRENT_USER.password)
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(idlingRes,idlingRes2)

    }

    @Test
    fun advancedSendMessageWithSteps(){
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
                .sendMessage("message2")
    }

    @Test
    fun advancedSendMessageWithPageObject() {
        val messageText = "message3"
        FriendsListPage().getListItem("Chandler Bing").click()
        val chatPage = ChatPage()
        chatPage.getName("Janice").isDisplayed()
        chatPage.sendMessageBtn.click()
        chatPage.clearHistoryBtn.click()
        chatPage.openOptionsMenu()
        chatPage.inputMessageText.typeText(messageText)
        chatPage.getListItem(messageText).text
            .isDisplayed()
            .hasText(messageText)
        Thread.sleep(1000)
    }

    @Test
    fun advancedTestSendMessageToJanice() {
        val messageText = "message4"
        val itemMatcher = hasDescendant(allOf(withId(R.id.tv_name), withText("Janice")))
        onView(withId(R.id.recycler_friends))
//            .perform(
//                RecyclerViewActions
//                    .scrollTo<RecyclerView.ViewHolder>(itemMatcher)
//            )
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
        onView(allOf(withText(messageText), withId(R.id.message_text))).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun testRecyclerView(){
        onView(withRecyclerView(withId(R.id.recycler_friends)).atItem(hasDescendant(withText("UNAGI")))).
            perform(click())
        Thread.sleep(2000)
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes)
    }


}