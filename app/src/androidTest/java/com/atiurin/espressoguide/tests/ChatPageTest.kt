package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.ChatActivity
import com.atiurin.espressoguide.activity.INTENT_CONTACT_ID_EXTRA_NAME
import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.data.repositories.MessageRepository
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUp
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import io.qameta.allure.android.annotations.DisplayName
import org.junit.Before
import org.junit.Test

class ChatPageTest : BaseTest() {
    companion object {
        const val ADD_SIMPLE_MESSAGE = "simple_message"
        const val ADD_SPECIAL_CHARS_MESSAGE = "special_chars_message"
    }

    private val contact = ContactsRepositoty.getContact(2)
    private val simpleMessage = Message(CURRENT_USER.id, contact.id, "SimpleText")
    private val specialCharsMessage = Message(CURRENT_USER.id, contact.id, "!@#$%^&*(){}:\",./<>?_+±§`~][")

    private val activityTestRule = CustomActivityTestRule(ChatActivity::class.java, false, false)
    private val setUpTearDownRule = SetUpTearDownRule()
        .addSetUp(ADD_SIMPLE_MESSAGE) {
            MessageRepository.clearChatMessages(contact.id)
            MessageRepository.addChatMessage(contact.id, simpleMessage)
        }
        .addSetUp(ADD_SPECIAL_CHARS_MESSAGE) {
            MessageRepository.clearChatMessages(contact.id)
            MessageRepository.addChatMessage(contact.id, specialCharsMessage)
        }
        .addSetUp {
            AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                CURRENT_USER.login,
                CURRENT_USER.password
            )
            val intent = Intent().putExtra(INTENT_CONTACT_ID_EXTRA_NAME, contact.id)
            activityTestRule.launchActivity(intent)
        }

    init {
        ruleSequence.add(setUpTearDownRule, activityTestRule)
    }

    @SetUp(ADD_SIMPLE_MESSAGE)
    @Test
    fun assertSimpleMessage() {
        ChatPage(contact).assertMessageDisplayed(simpleMessage.text)
    }

    @SetUp(ADD_SPECIAL_CHARS_MESSAGE)
    @Test
    fun assertSpecialCharsMessage() {
        ChatPage(contact).assertMessageDisplayed(specialCharsMessage.text)
    }

    @Test
    fun assertChatTitle() {
        ChatPage(contact).assertPageDisplayed()
    }

    @Test
    fun addNewMessage() {
        val messageText = "new message"
        ChatPage(contact) {
            sendMessage(messageText)
            assertMessageDisplayed(messageText)
        }
    }

    @Test
    fun assertMessagePosition() {
        val messageText = "position message"
        val initialMaxPosition = MessageRepository.getChatMessagesCount(contact.id) - 1
        ChatPage(contact) {
            sendMessage(messageText)
            assertMessageTextAtPosition(initialMaxPosition + 1, messageText)
        }
    }
}