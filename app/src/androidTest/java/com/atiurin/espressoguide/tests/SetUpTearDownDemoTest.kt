package com.atiurin.espressoguide.tests

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactRepositoty
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUp
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import com.atiurin.espressopageobject.testlifecycle.setupteardown.TearDown
import org.junit.Rule
import org.junit.Test

class SetUpTearDownDemoTest : BaseTest() {
    lateinit var chatPage: ChatPage
    private val contact = ContactRepositoty.getContact("Chandler Bing")

    companion object{
        const val FIRST_SETUP = "FirstSetup"
        const val SECOND_SETUP = "SecondSetup"
        const val FIRST_TEARDOWN = "FirstTearDown"
        const val SECOND_TEARDOWN = "SecondTearDown"
        const val LOG_TAG = "Life>>"
    }
    @get:Rule
    val setupRule = SetUpTearDownRule()
        .addSetUp {
            Log.d(LOG_TAG, "common setup")
            //make login into app before test starts and activity is launched
            //to make sure that user is logged in when test starts
            AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                CURRENT_USER.login,
                CURRENT_USER.password
            )
        }
        .addSetUp(FIRST_SETUP) { Log.d(LOG_TAG, "first setup") }
        .addSetUp(SECOND_SETUP) { Log.d(LOG_TAG, "second setup") }
        .addTearDown { Log.d(LOG_TAG, "common tear down") }
        .addTearDown(FIRST_TEARDOWN) { Log.d(LOG_TAG, "first tear down") }
        .addTearDown(SECOND_TEARDOWN) { Log.d(LOG_TAG, "second tear down") }

    @get:Rule
    val mActivityRule = CustomActivityTestRule(MainActivity::class.java)

    @Test
    fun friendsItemCheck() {
        Log.d(LOG_TAG, "test friendsItemCheck")
        FriendsListPage {
            assertName("Janice")
            assertStatus("Janice", "Oh. My. God")
        }
    }

    @SetUp(FIRST_SETUP)
    @TearDown(SECOND_TEARDOWN)
    @Test
    fun sendMessage() {
        Log.d(LOG_TAG, "test sendMessage")
        Log.d("condition", "common setup")
        chatPage = FriendsListPage().openChat(contact)
        chatPage {
            clearHistory()
            sendMessage("test message")
        }

    }

    @SetUp(SECOND_SETUP, FIRST_SETUP)
    @TearDown(SECOND_TEARDOWN)
    @Test
    fun checkMessagesPositionsInChat() {
        Log.d(LOG_TAG, "test checkMessagesPositionsInChat")
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
    @Test
    fun specialFailedTestForAllureReport() {
        Log.d(LOG_TAG, "test specialFailedTestForAllureReport")
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