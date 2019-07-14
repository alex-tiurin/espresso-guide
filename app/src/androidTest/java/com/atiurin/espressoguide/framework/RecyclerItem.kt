package com.atiurin.espressoguide.framework

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import org.hamcrest.Matcher

open class RecyclerItem(private val recyclerViewMatcher: Matcher<View>, private val itemViewMatcher: Matcher<View>, autoScroll: Boolean = true){

    init {
        if (autoScroll){
            scrollToItem()
        }
    }

    fun scrollToItem() : RecyclerItem = apply {
        onView(recyclerViewMatcher).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher))
    }

    fun click() = apply { this.get().click() }

    fun isDisplayed() = apply { this.get().isDisplayed() }

    private fun get() : Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItem(itemViewMatcher)
    }

    fun getChildMatcher(childMatcher: Matcher<View>)= withRecyclerView(recyclerViewMatcher)
            .atItemChild(itemViewMatcher, childMatcher)
}