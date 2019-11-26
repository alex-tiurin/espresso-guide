package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.espressopageobject.core.action.ViewActionConfig
import com.atiurin.espressopageobject.core.assertion.ViewAssertionConfig
import org.junit.*

class DemoEspressoTest : BaseTest() {
    companion object {
        @BeforeClass
        @JvmStatic
        fun switchOffFailureHandler() {
            ViewActionConfig.allowedExceptions.clear()// disable failure handler
            ViewAssertionConfig.allowedExceptions.clear()// disable failure handler
        }
    }

    @get:Rule
    val mActivityRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    private val contactsIdlingResource = ContactsIdlingResource.getInstanceFromTest()

    @Before
    fun registerResource() {
        //make login into app before test start and activity launched
        //to be sure that user is logged in when test starts
        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
            CURRENT_USER.login,
            CURRENT_USER.password
        )
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(contactsIdlingResource)
    }

    @Test
    fun friendsItemCheck() {
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice", "Oh. My. God")
    }

    @Test
    fun sendMessage() {
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
                  .sendMessage("test message")

    }

    @Test
    fun checkMessagesPositionsInChat() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        FriendsListPage().openChat("Janice")
        ChatPage {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, firstMessage)
            assertMessageTextAtPosition(1, secondMessage)
        }
    }

    @Test
    fun specialFailedTestForAllureReport() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        FriendsListPage().openChat("Janice")
        ChatPage {
            clearHistory()
            sendMessage(firstMessage)
            sendMessage(secondMessage)
            assertMessageTextAtPosition(0, secondMessage)
            assertMessageTextAtPosition(1, firstMessage)
        }
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(contactsIdlingResource)
    }
}