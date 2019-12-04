package com.atiurin.espressoguide

import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.managers.AccountManager
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

class IntegrationTest{
    @Test
    @Ignore
    fun testLoginWithValidData() {
        val validPassword = "1234"
        val validUserName = "joey"
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val manager = AccountManager(context)
        manager.logout()
        manager.login(validUserName, validPassword)
        Assert.assertTrue(manager.isLogedIn())
    }
}