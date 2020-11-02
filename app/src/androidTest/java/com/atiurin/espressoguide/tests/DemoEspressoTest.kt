package com.atiurin.espressoguide.tests

import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.espressopageobject.core.espresso.action.ViewActionConfig
import com.atiurin.espressopageobject.core.espresso.assertion.ViewAssertionConfig
//import com.atiurin.espressopageobject.core.espresso.action.ViewActionConfig
//import com.atiurin.espressopageobject.core.espresso.assertion.ViewAssertionConfig
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import io.qameta.allure.android.annotations.DisplayName
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test


/**
 * There are several ways of how to use page object class.
 * Use the one you prefer
 */
class DemoEspressoTest : BaseTest() {
    companion object {
        @BeforeClass
        @JvmStatic
        fun switchOffFailureHandler() {
            ViewActionConfig.allowedExceptions.clear()// disable failure handler
            ViewAssertionConfig.allowedExceptions.clear()// disable failure handler
        }
    }

    private val contact = ContactsRepositoty.getContact("Chandler Bing")

    init {
        ruleSequence
            .add(SetUpTearDownRule()
                .addSetUp {
                    //make login into app before test starts and activity is launched
                    //to make sure that user is logged in when test starts
                    AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                        CURRENT_USER.login,
                        CURRENT_USER.password
                    )
                })
            .addLast(CustomActivityTestRule(MainActivity::class.java))
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
        val chatPage = FriendsListPage.openChat(contact)
        chatPage.clearHistory()
        chatPage.sendMessage ("test message")
    }

    @Test
    fun checkMessagesPositionsInChat() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        FriendsListPage { openChat(contact) }
        ChatPage {
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
        val janiceContact = ContactsRepositoty.getContact("Janice")
        val chatPage = FriendsListPage.openChat(janiceContact)
        chatPage {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, secondMessage)
            assertMessageTextAtPosition(1, firstMessage)
        }
    }
}