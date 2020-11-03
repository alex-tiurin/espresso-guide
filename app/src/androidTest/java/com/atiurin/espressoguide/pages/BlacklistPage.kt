package com.atiurin.espressoguide.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.framework.BasePage
import com.atiurin.espressoguide.framework.reporting.step
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

object BlacklistPage : BasePage<BlacklistPage>() {
    private val itemsList = withId(R.id.recycler_blacklist)

    override fun assertPageDisplayed() = apply {
        step("Assert blacklist page displayed") {
            itemsList.isDisplayed()
        }
    }

    fun assertContactDisplayed(name: String) = apply {
        step("Assert contact with name $name is displayed in blacklist") {
            getListItem(name).isDisplayed()
        }
    }

    private fun getListItem(name: String): BlacklistItem {
        return BlacklistItem(itemsList, hasDescendant(allOf(withId(R.id.tv_name), withText(name))))
    }

    private fun getItemAtPosition(position: Int): BlacklistItem {
        return BlacklistItem(itemsList, position)
    }

    private class BlacklistItem : RecyclerViewItem {
        constructor(list: Matcher<View>, item: Matcher<View>) : super(list, item)
        constructor(list: Matcher<View>, position: Int) : super(list, position)

        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
        val avatar = getChildMatcher(withId(R.id.avatar))
    }
}