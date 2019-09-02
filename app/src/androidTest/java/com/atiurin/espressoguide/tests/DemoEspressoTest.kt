package com.atiurin.espressoguide.tests

import android.content.Intent
import android.util.Log
import androidx.test.espresso.IdlingRegistry
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
import org.junit.*
import ru.tinkoff.allure.android.deviceScreenshot

class DemoEspressoTest{

    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    companion object {
        @BeforeClass @JvmStatic
        fun beforeClass(){
            ViewActionsConfig.beforeAction = {
                Log.d("Espresso", "take screenshot before")
                deviceScreenshot("screenshot")
            }

            ViewActionsConfig.afterAction = {
                Log.d("Espresso", "made action")
            }

            ViewAssertionsConfig.beforeAssertion = {
                Log.d("Espresso", "take screenshot before")
                deviceScreenshot("assert screenshot")
            }
        }
    }
    @Before
    fun registerResource() {
        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(CURRENT_USER.login, CURRENT_USER.password)
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(idlingRes)
    }
    @Test
    fun friendsItemCheck(){
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice","Oh. My. God")
    }
    @Test
    fun sendMessage(){
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage("test message")
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes)
    }
}