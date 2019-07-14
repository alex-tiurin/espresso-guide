package com.atiurin.espressoguide

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressoguide.managers.AccountManager
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.junit.Assert
import org.robolectric.Robolectric

@RunWith(RobolectricTestRunner::class)
class RobolectricTest {
    @Test
    fun testLoginWithValidData() {
        val validPassword = "1234"
        val validUserName = "joey"

        val context = ApplicationProvider
            .getApplicationContext<Context>()
        val result = AccountManager(context)
            .login(validUserName, validPassword)
        Assert.assertTrue(result)
    }

    @Test
    fun testLoginWithInvalidPassword(){
        val invalidPassword = "777777"
        val validUserName = "joey"
        val result = AccountManager(context = ApplicationProvider.getApplicationContext())
            .login(user = validUserName,password = invalidPassword)
        Assert.assertFalse("Expected login result is false but get $result", result)
    }
    @Test
    fun testLoginWithInvalidUserName(){
        val invalidPassword = "1234"
        val validUserName = "chandler"
        val result = AccountManager(context = ApplicationProvider.getApplicationContext())
            .login(user = validUserName,password = invalidPassword)
        Assert.assertFalse("Expected login result is false but get $result", result)
    }
}