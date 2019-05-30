package com.atiurin.espressoguide.managers

import android.content.Context

class AccountManager(val context: Context){
    val USER_KEY = "username"
    val PASSWORD_KEY = "password"

    fun login(user: String, password: String) : Boolean{
        val expectedUserName = "joey"
        val expectedPassword = "1234"
        var success = false
        if ((user == expectedUserName) &&(password == expectedPassword)){
            success = true
            with(PrefsManager(context)){
                savePref(USER_KEY, user)
                savePref(PASSWORD_KEY, password)
            }

        }
        return success
    }

    fun isLogedIn() : Boolean{
        var userName = ""
        var password = ""
        with(PrefsManager(context)){
            userName = getPref(USER_KEY)
            password = getPref(PASSWORD_KEY)
        }
        if (userName.isEmpty() || password.isEmpty()) return false
        return true
    }

    fun logout(){
        with(PrefsManager(context)){
            remove(USER_KEY)
            remove(PASSWORD_KEY)
        }
    }

}