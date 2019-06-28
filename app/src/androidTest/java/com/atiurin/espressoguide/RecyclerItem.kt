package com.atiurin.espressoguide

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

    fun scrollToItem() : RecyclerItem{
        onView(recyclerViewMatcher).perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher))
        return this
    }

    fun getItem() : Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItem(itemViewMatcher)
    }

    fun getChildMatcher(childMatcher: Matcher<View>): Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItemChild(itemViewMatcher, childMatcher)
    }
}