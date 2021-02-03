package com.atiurin.espressoguide.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.framework.*
import com.atiurin.espressoguide.framework.reporting.step
import com.atiurin.ultron.core.espresso.UltronEspresso
import com.atiurin.ultron.extensions.click
import com.atiurin.ultron.extensions.hasText
import com.atiurin.ultron.extensions.isDisplayed
import com.atiurin.ultron.extensions.typeText
import com.atiurin.ultron.page.Page
import com.atiurin.ultron.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.recyclerview.withRecyclerView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

object ChatPage : Page<ChatPage>() {
    init {
        Logger.debug(">>>>>Chat page inited")
    }
    lateinit var contact: Contact
    private val list = withRecyclerView(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)

    private fun getMessageListItem(text: String): ChatRecyclerItem {
        return list.getItem(hasDescendant(
                allOf(
                    withId(R.id.message_text),
                    withText(text)
                )
            )
        )
    }

    private fun getMessageListItemAtPosition(position: Int): ChatRecyclerItem {
        return list.getItem(position)
    }

    private fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }

    class ChatRecyclerItem : UltronRecyclerViewItem() {
        val text by lazy { getChild(withId(R.id.message_text)) }
    }

    fun assertPageDisplayed() = apply {
        step("Assert chat page is displayed") {
            list.isDisplayed()
            inputMessageText.isDisplayed()
        }
    }

    fun assertChatTitle() = apply {
        step("Assert chat with contact '${contact.name}' has correct title") {
            getTitle(contact.name).isDisplayed()
        }
    }

    fun sendMessage(text: String) = apply {
        step("Send message with text '$text'") {
            inputMessageText.typeText(text)
            sendMessageBtn.click()
            getMessageListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }

    fun clearHistory() = apply {
        step("Clear chat history") {
            UltronEspresso.openContextualActionModeOverflowMenu()
            clearHistoryBtn.click()
        }
    }

    fun assertMessageDisplayed(text: String) = apply {
        step("Assert message with text is displayed") {
            getMessageListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }

    fun assertMessageTextAtPosition(position: Int, text: String) = apply {
        step("Assert message at position $position has text '$text' and displayed") {
            getMessageListItemAtPosition(position).text
                .hasText(text)
                .isDisplayed()
        }
    }
}

