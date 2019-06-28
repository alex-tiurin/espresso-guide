package com.atiurin.espressoguide

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matcher

fun scrollTo(itemViewMatcher: Matcher<View>): ViewAction {
    return RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher)
}

private fun checkView(viewMatcher: Matcher<View>, condition: Matcher<View>) {
    onView(viewMatcher).check(matches(condition))
}

fun Matcher<View>.isDisplayed(): Matcher<View> {
    checkView(this, ViewMatchers.isDisplayed())
    return this
}
fun Matcher<View>.hasText(text: String): Matcher<View> {
    checkView(this, withText(text))
    return this
}
private fun actionOnView(viewMatcher: Matcher<View>, action: ViewAction){
    onView(viewMatcher).perform(action)
}

private fun actionInDialog(viewMatcher: Matcher<View>, action: ViewAction){
    onView(viewMatcher).inRoot(isDialog()).perform(action)
}

fun Matcher<View>.click(){
    actionOnView(this, ViewActions.click())
}