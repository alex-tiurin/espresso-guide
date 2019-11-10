package com.atiurin.espressoguide.tests

import android.content.Intent
import android.util.Log
import androidx.test.espresso.IdlingRegistry
import androidx.test.internal.runner.listener.InstrumentationResultPrinter
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.activity.MainActivity
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.pages.ChatPage
import com.atiurin.espressoguide.pages.FriendsListPage
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig
import junit.textui.ResultPrinter
import org.junit.*
import ru.tinkoff.allure.android.deviceScreenshot

class DemoEspressoTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun switchOffFailureHandler() {
//            ViewActionsConfig.allowedExceptions.clear()// disable failure handler
//            ViewAssertionsConfig.allowedExceptions.clear()// disable failure handler
        }
    }

    private val contactsIdlingResource = ContactsIdlingResource.getInstanceFromTest()

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)


    @Before
    fun registerResource() {
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
        ChatPage().clearHistory()
            .sendMessage(firstMessage)
            .sendMessage(secondMessage)
            .assertMessageTextAtPosition(0, firstMessage)
            .assertMessageTextAtPosition(1, secondMessage)
    }

    @Test
    fun specialFailedTestForAllureReport() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage(firstMessage)
            .sendMessage(secondMessage)
            .assertMessageTextAtPosition(0, secondMessage)
            .assertMessageTextAtPosition(1, firstMessage)
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(contactsIdlingResource)
    }
}