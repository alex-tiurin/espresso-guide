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
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class BadDataPreparationTest : BaseTest() {
    companion object {
        private val contact = ContactsRepositoty.getContact(2)
        private val simpleMessage = Message(CURRENT_USER.id, contact.id, "SimpleText")
        private val specialCharsMessage = Message(CURRENT_USER.id, contact.id, "!@#$%^&*(){}:\",./<>?_+±§`~][")
        private val longMessage = Message(CURRENT_USER.id, contact.id, InstrumentationRegistry.getInstrumentation().context.assets.open("long_message.txt").reader().readText())
        @BeforeClass
        @JvmStatic
        fun prepareData() {
            MessageRepository.clearChatMessages(contact.id)
            MessageRepository.addChatMessage(contact.id, longMessage)
            MessageRepository.addChatMessage(contact.id, simpleMessage)
            MessageRepository.addChatMessage(contact.id, specialCharsMessage)
        }
    }
    private val activityTestRule = ActivityTestRule(ChatActivity::class.java, false, false)

    @Before
    fun prepareConditions(){
        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        val intent = Intent().putExtra(INTENT_CONTACT_ID_EXTRA_NAME, contact.id)
        activityTestRule.launchActivity(intent)
    }

    @Test
    fun assertSimpleMessage() {
        ChatPage(contact).assertMessageDisplayed(simpleMessage.text)
    }

    @Test
    fun assertSpecialMessage() {
        ChatPage(contact).assertMessageDisplayed(specialCharsMessage.text)
    }
    @Test
    fun assertLongMessage() {
        ChatPage(contact).assertMessageDisplayed(longMessage.text)
    }
}