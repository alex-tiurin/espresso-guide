package com.atiurin.espressoguide.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.framework.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class ChatPage : Page {
    override fun assertPageDisplayed() {
        list.isDisplayed()
    }

    val list = withId(R.id.messages_list)
    val clearHistoryBtn = withText("Clear history")
    val inputMessageText = withId(R.id.message_input_text)
    val sendMessageBtn = withId(R.id.send_button)

    fun getListItem(text: String): ChatRecyclerItem{
        return ChatRecyclerItem(
            list,
            ViewMatchers.hasDescendant(
                Matchers.allOf(
                    withId(R.id.messageText),
                    withText(text)
                )
            )
        )
    }

    class ChatRecyclerItem(list: Matcher<View>, item: Matcher<View>) : RecyclerItem(list, item) {
        val text = getChildMatcher(withId(R.id.messageText))
    }

    companion object {
        fun sendMessage(text: String) = apply {
            step("Send message with text '$text"){
                val chatPage = ChatPage()
                chatPage.inputMessageText.typeText(text)
                chatPage.sendMessageBtn.click()
                chatPage.getListItem(text).text
                    .isDisplayed()
                    .hasText(text)
            }
        }

        fun clearHistory() = apply {
            step("Clear chat history"){
                val chatPage = ChatPage()
                chatPage.openOptionsMenu()
                chatPage.clearHistoryBtn.click()
            }
        }

        fun assertMessageDisplayed(text: String){
            ChatPage().getListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }
}