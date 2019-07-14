package com.atiurin.espressoguide.framework

import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry

interface Page{
    fun assertPageDisplayed()

    fun openOptionsMenu(){
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().context)
    }
}