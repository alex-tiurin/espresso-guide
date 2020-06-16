package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
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
        const val ADD_LONG_MESSAGE = "long_message"
    }
    private val contact = ContactsRepositoty.getContact(2)
    private val simpleMessage = Message(CURRENT_USER.id, contact.id, "SimpleText")
    private val specialCharsMessage = Message(CURRENT_USER.id, contact.id, "!@#$%^&*(){}:\",./<>?_+±§`~][")
    private val longMessage = Message(CURRENT_USER.id, contact.id, InstrumentationRegistry.getInstrumentation().context.assets.open("long_message.txt").reader().readText())

    private val activityTestRule = ActivityTestRule(ChatActivity::class.java, false, false)
    private val setUpTearDownRule = SetUpTearDownRule()
        .addSetUp { MessageRepository.clearChatMessages(contact.id) }
        .addSetUp(ADD_SIMPLE_MESSAGE) {
            MessageRepository.addChatMessage(contact.id, simpleMessage)
        }
        .addSetUp(ADD_SPECIAL_CHARS_MESSAGE) {
            MessageRepository.addChatMessage(contact.id, specialCharsMessage)
        }
        .addSetUp(ADD_LONG_MESSAGE) {
            MessageRepository.addChatMessage(contact.id, longMessage)
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

    @Test @SetUp(ADD_SIMPLE_MESSAGE)
    fun assertSimpleMessage() {
        ChatPage(contact).assertMessageDisplayed(simpleMessage.text)
    }

    @Test @SetUp(ADD_SPECIAL_CHARS_MESSAGE)
    fun assertSpecialCharsMessage() {
        ChatPage(contact).assertMessageDisplayed(specialCharsMessage.text)
    }

    @Test @SetUp(ADD_LONG_MESSAGE)
    fun assertLongMessage() {
        ChatPage(contact).assertMessageDisplayed(longMessage.text)
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

    @Test @SetUp(ADD_SIMPLE_MESSAGE, ADD_SPECIAL_CHARS_MESSAGE)
    fun assertMessagePosition() {
        val messageText = "position message"
        val initialMaxPosition = MessageRepository.getChatMessagesCount(contact.id) - 1
        ChatPage(contact) {
            sendMessage(messageText)
            assertMessageTextAtPosition(initialMaxPosition + 1, messageText)
        }
    }
}