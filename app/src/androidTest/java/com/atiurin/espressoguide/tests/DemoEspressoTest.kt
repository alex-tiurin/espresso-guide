package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactRepositoty
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.espressopageobject.core.action.ViewActionConfig
import com.atiurin.espressopageobject.core.assertion.ViewAssertionConfig
import io.qameta.allure.android.annotations.DisplayName
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

class DemoEspressoTest : BaseTest() {
    companion object {
        @BeforeClass
        @JvmStatic
        fun switchOffFailureHandler() {
            ViewActionConfig.allowedExceptions.clear()// disable failure handler
            ViewAssertionConfig.allowedExceptions.clear()// disable failure handler
        }
    }
    lateinit var chatPage: ChatPage
    private val contact = ContactRepositoty.getContact("Chandler Bing")

    @get:Rule
    val mActivityRule = CustomActivityTestRule(
        MainActivity::class.java,
        initialTouchMode = false,
        launchActivity = false
    )

    @Before
    fun backgroundLogin() {
        //make login into app before test starts and activity is launched
        //to make sure that user is logged in when test starts
        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        mActivityRule.launchActivity(Intent())
    }

    @Test
    fun friendsItemCheck() {
        FriendsListPage {
            assertName("Janice")
            assertStatus("Janice", "Oh. My. God")
        }
    }

    @Test
    fun sendMessage() {
        chatPage = FriendsListPage().openChat(contact)
        chatPage {
            clearHistory()
            sendMessage("test message")
        }

    }

    @Test
    fun checkMessagesPositionsInChat() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        val chatPage = FriendsListPage().openChat(contact)
        chatPage {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, firstMessage)
            assertMessageTextAtPosition(1, secondMessage)
        }
    }

    /**
     * Test should fail
     */
    @DisplayName("Special failed test for allure report demo")
    @Test
    fun specialFailedTestForAllureReport() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        val janiceContact = ContactRepositoty.getContact("Janice")
        FriendsListPage().openChat(janiceContact)
        ChatPage(janiceContact) {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, secondMessage)
            assertMessageTextAtPosition(1, firstMessage)
        }
    }
}