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
import com.atiurin.espressoguide.idlingresources.ContactsIdlingResource
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

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun registerResource() {
        AccountManager(getInstrumentation().targetContext).login(CURRENT_USER.login, CURRENT_USER.password)
        val intent = Intent(getInstrumentation().targetContext, MainActivity::class.java)
        mActivityRule.launchActivity(intent)
        IdlingRegistry.getInstance().register(idlingRes)

    }

    @Test
    fun advancedSendMessageWithSteps(){
        FriendsListPage.openChat("Janice")
        ChatPage.clearHistory()
                .sendMessage("message1")
    }

    @Test
    fun advancedSendMessageWithPageObject() {
        val messageText = "message1"
        FriendsListPage().getListItem("Janice").click()
        val chatPage = ChatPage()
        chatPage.openOptionsMenu()
        chatPage.clearHistoryBtn.click()
        chatPage.inputMessageText.typeText(messageText)
        chatPage.sendMessageBtn.click()
        chatPage.getListItem(messageText).text
            .isDisplayed()
            .hasText(messageText)
        Thread.sleep(1000)
    }

    @Test
    fun advancedTestSendMessageToJanice() {
        val messageText = "message1"
        val itemMatcher = hasDescendant(allOf(withId(R.id.tv_name), withText("Janice")))
        onView(withId(R.id.recycler_friends))
            .perform(
                RecyclerViewActions
                    .scrollTo<RecyclerView.ViewHolder>(itemMatcher)
            )
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
        onView(withText(messageText)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes)
    }


}