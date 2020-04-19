package com.atiurin.espressoguide.tests

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.activity.SettingsActivity
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.fragment.settings.BlacklistFragment
import com.atiurin.espressoguide.fragment.settings.SettingsFragmentNavigator
import com.atiurin.espressoguide.pages.BlacklistPage
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUp
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BlacklistTests : BaseTest() {
    companion object {
        const val ADD_CONTACT_TO_BLACKLIST = "ADD_CONTACT_TO_BLACKLIST"
        const val CLEAR_BLACKLIST = "CLEAR_BLACKLIST"
    }

    val contact = ContactsRepositoty.getContact(2)

    private val activityRule = ActivityTestRule(SettingsActivity::class.java, false, false)
    private val setUpTearDownRule = SetUpTearDownRule()
        .addSetUp(CLEAR_BLACKLIST) {
            ContactsRepositoty.clearBlacklist()
        }
        .addSetUp(ADD_CONTACT_TO_BLACKLIST) {
            ContactsRepositoty.addToBlacklist(contact.id)
        }
        .addSetUp {
            activityRule.launchActivity(Intent())
            activityRule.runOnUiThread {
                SettingsFragmentNavigator.go(BlacklistFragment::class.java)
            }
        }

    init {
        ruleSequence.add(activityRule, setUpTearDownRule)
    }


    @SetUp(CLEAR_BLACKLIST, ADD_CONTACT_TO_BLACKLIST)
    @Test
    fun testItemDisplayed() {
        BlacklistPage().assertContactDisplayed(contact.name)
    }
}