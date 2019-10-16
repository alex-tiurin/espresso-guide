package com.atiurin.espressoguide.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.framework.*
import com.atiurin.espressopageobject.extensions.click
import com.atiurin.espressopageobject.extensions.hasText
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.extensions.typeText
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf

class ChatPage : Page {
    override fun assertPageDisplayed() = apply{
        step("Assert friends list page displayed") {
            list.isDisplayed()
        }
    }

    private val list = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)

    private fun getListItem(text: String): ChatRecyclerItem {
        return ChatRecyclerItem(
            list,
            ViewMatchers.hasDescendant(
                allOf(
                    withId(R.id.message_text),
                    withText(text)
                )
            )
        )
    }

    private fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }

    private class ChatRecyclerItem(list: Matcher<View>, item: Matcher<View>) : RecyclerViewItem(list, item) {
        val text = getChildMatcher(withId(R.id.message_text))
    }

    fun sendMessage(text: String) = apply {
        step("Send message with text '$text") {
            inputMessageText.typeText(text)
            sendMessageBtn.click()
            getListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }

    fun clearHistory() = apply {
        step("Clear chat history") {
            openOptionsMenu()
            clearHistoryBtn.click()
        }
    }

    fun assertMessageDisplayed(text: String) {
        getListItem(text).text
            .isDisplayed()
            .hasText(text)
    }
}