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
import org.hamcrest.Matchers.allOf

class ChatPage : Page {
    constructor(action: ChatPage.() -> Unit){
        this.action()
    }
    constructor()
    override fun assertPageDisplayed() = apply {
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

    private fun getListItemAtPosition(position: Int): ChatRecyclerItem {
        return ChatRecyclerItem(list, position)
    }

    private fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }

    private class ChatRecyclerItem : RecyclerViewItem {
        constructor(list: Matcher<View>, item: Matcher<View>) : super(list, item)
        constructor(list: Matcher<View>, position: Int) : super(list, position)

        val text = getChildMatcher(withId(R.id.message_text))
    }

    fun sendMessage(text: String) = apply {
        step("Send message with text '$text'") {
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

    fun assertMessageDisplayed(text: String) = apply {
        step("Assert message with text is displayed") {
            getListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }

    fun assertMessageTextAtPosition(position: Int, text: String) = apply {
        step("Assert message at position $position has text '$text' and displayed") {
            getListItemAtPosition(position).text
                .hasText(text)
                .isDisplayed()
        }
    }
}

