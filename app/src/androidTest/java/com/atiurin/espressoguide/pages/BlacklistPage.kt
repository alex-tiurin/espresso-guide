package com.atiurin.espressoguide.pages

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.framework.reporting.step
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.page.Page
import org.hamcrest.CoreMatchers.allOf

object BlacklistPage : Page<BlacklistPage>() {
    private val itemsList = withRecyclerView(R.id.recycler_blacklist)

    fun assertPageDisplayed() = apply {
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
        return itemsList.getItem(hasDescendant(allOf(withId(R.id.tv_name), withText(name))))
    }

    private fun getItemAtPosition(position: Int): BlacklistItem {
        return itemsList.getItem(position)
    }

    class BlacklistItem : UltronRecyclerViewItem() {
        val name by lazy { getChild(withId(R.id.tv_name)) }
        val status  by lazy {  getChild(withId(R.id.tv_status)) }
        val avatar by lazy { getChild(withId(R.id.avatar)) }
    }
}