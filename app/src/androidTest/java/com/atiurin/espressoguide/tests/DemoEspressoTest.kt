package com.atiurin.espressoguide.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.*


/**
 * There are several ways of how to use page object class.
 * Use the one you prefer
 */
class DemoEspressoTest : BaseTest() {
    private val contact = ContactsRepositoty.getContact("Chandler Bing")

    init {
        ruleSequence
            .add(
                SetUpRule()
                    .add {
                        //make login into app before test starts and activity is launched
                        //to make sure that user is logged in when test starts
                        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                            CURRENT_USER.login,
                            CURRENT_USER.password
                        )
                        ChatPage.contact = contact
                    })
            .addLast(ActivityScenarioRule(MainActivity::class.java))
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
        chatPage.sendMessage("test message")
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