package com.atiurin.espressoguide.tests

import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.framework.CustomActivityTestRule
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.ultron.testlifecycle.setupteardown.SetUp
import com.atiurin.ultron.testlifecycle.setupteardown.SetUpRule
import com.atiurin.ultron.testlifecycle.setupteardown.TearDown
import com.atiurin.ultron.testlifecycle.setupteardown.TearDownRule
import org.junit.*

class SetUpTearDownDemoTest : BaseTest() {
    lateinit var chatPage: ChatPage
    private val contact = ContactsRepositoty.getContact("Chandler Bing")

    companion object {
        const val FIRST_SETUP = "FirstSetup"
        const val SECOND_SETUP = "SecondSetup"
        const val FIRST_TEARDOWN = "FirstTearDown"
        const val SECOND_TEARDOWN = "SecondTearDown"
    }

    init {
        ruleSequence
            .addFirst(
                SetUpRule()
                    .add {
                        Logger.life("common setup")
                        //make login into app before test starts and activity is launched
                        //to make sure that user is logged in when test starts
                        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                            CURRENT_USER.login,
                            CURRENT_USER.password
                        )
                    }
                    .add(FIRST_SETUP) { Logger.life("first setup") }
                    .add(SECOND_SETUP) { Logger.life("second setup") } ,
                TearDownRule()
                    .add { Logger.life("common tear down") }
                    .add(FIRST_TEARDOWN) { Logger.life("first tear down") }
                    .add(SECOND_TEARDOWN) { Logger.life("second tear down") })
            .addLast(CustomActivityTestRule(MainActivity::class.java))
    }

    @Test
    fun friendsItemCheck() {
        Logger.life("test friendsItemCheck")
        FriendsListPage {
            assertName("Janice")
            assertStatus("Janice", "Oh. My. God")
        }
    }

    @SetUp(FIRST_SETUP)
    @TearDown(SECOND_TEARDOWN)
    @Test
    fun sendMessage() {
        Logger.life("test sendMessage")
        chatPage = FriendsListPage.openChat(contact)
        chatPage {
            clearHistory()
            sendMessage("test message")
        }

    }

    @SetUp(SECOND_SETUP, FIRST_SETUP)
    @TearDown(SECOND_TEARDOWN)
    @Test
    fun checkMessagesPositionsInChat() {
        Logger.life("test checkMessagesPositionsInChat")
        val firstMessage = "first message"
        val secondMessage = "second message"
        val chatPage = FriendsListPage.openChat(contact)
        chatPage {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, firstMessage)
            assertMessageTextAtPosition(1, secondMessage)
        }
    }
}