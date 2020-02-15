package com.atiurin.espressoguide.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.Tags
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.framework.*
import com.atiurin.espressoguide.framework.reporting.step
import com.atiurin.espressopageobject.extensions.hasText
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf

class FriendsListPage(action: FriendsListPage.() -> Unit = {}) : Page {
    lateinit var chatPage: ChatPage

    private val list = withTagValue(`is`(Tags.CONTACTS_LIST))

    private fun getListItem(title: String): FriendRecyclerItem {
        return FriendRecyclerItem(
            list,
            hasDescendant(
                allOf(withId(R.id.tv_name), withText(title))
            )
        )
    }

    private class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>) :
        RecyclerViewItem(list, item) {
        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
    }

    override fun assertPageDisplayed() = apply {
        step("Assert friends list page displayed") {
            list.isDisplayed()
        }
    }

    fun openChat(contact: Contact): ChatPage {
        step("Open chat with friend '${contact.name}'") {
            getListItem(contact.name).click()
            chatPage = ChatPage(contact).assertPageDisplayed()
        }
        return chatPage
    }

    fun assertStatus(name: String, status: String) = apply {
        step("Assert friend with name '$name' has status '$status'") {
            getListItem(name).status.hasText(status)
        }
    }

    fun assertName(nameText: String) = apply {
        step("Assert friend name '$nameText' in the right place") {
            getListItem(nameText).name.hasText(nameText)
        }
    }

    init {
        this.action()
    }
}