package com.atiurin.espressoguide.pages

import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.Tags
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.framework.*
import com.atiurin.espressoguide.framework.reporting.step
import com.atiurin.ultron.core.espresso.recyclerview.UltronRecyclerViewItem
import com.atiurin.ultron.core.espresso.recyclerview.withRecyclerView
import com.atiurin.ultron.extensions.hasText
import com.atiurin.ultron.page.Page
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf

object FriendsListPage : Page<FriendsListPage>() {
    private val list = withRecyclerView(withTagValue(`is`(Tags.CONTACTS_LIST)))

    private fun getFriendListItem(title: String): FriendRecyclerItem {
        return list.getItem(hasDescendant(allOf(withId(R.id.tv_name), withText(title))))
    }

    class FriendRecyclerItem : UltronRecyclerViewItem() {
        val name by lazy { getChild(withId(R.id.tv_name)) }
        val status by lazy { getChild(withId(R.id.tv_status)) }
    }

    fun assertPageDisplayed() = apply {
        step("Assert friends list page displayed") {
            list.isDisplayed()
        }
    }

    fun openChat(contact: Contact): ChatPage {
        return step("Open chat with friend '${contact.name}'") {
            getFriendListItem(contact.name).click()
            ChatPage {
                this.contact = contact
                assertPageDisplayed()
                assertChatTitle()
            }
        }
    }

    fun assertStatus(name: String, status: String) = apply {
        step("Assert friend with name '$name' has status '$status'") {
            getFriendListItem(name).status.hasText(status)
        }
    }

    fun assertName(nameText: String) = apply {
        step("Assert friend name '$nameText' in the right place") {
            getFriendListItem(nameText).name.hasText(nameText)
        }
    }
}