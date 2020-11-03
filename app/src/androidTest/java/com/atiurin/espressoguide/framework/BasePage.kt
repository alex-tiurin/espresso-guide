package com.atiurin.espressoguide.framework

import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry
import com.atiurin.espressopageobject.page.Page

abstract class BasePage<out T : Page<T>> : Page<T>() {
    abstract fun assertPageDisplayed() : T

    fun openOptionsMenu() = apply {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().context)
    }
}
