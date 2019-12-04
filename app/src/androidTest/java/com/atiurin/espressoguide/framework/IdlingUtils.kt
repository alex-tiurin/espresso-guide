package com.atiurin.espressoguide.framework

import com.atiurin.espressoguide.idlingresources.ContactsIdling
import com.atiurin.espressoguide.idlingresources.IdlingScope
import com.atiurin.espressoguide.idlingresources.MessagesIdling

fun getDefaultIdlingScope(): IdlingScope {
    return object : IdlingScope {
        override val messagesIdling: MessagesIdling = MessagesIdling()
        override var contactsIdling: ContactsIdling = ContactsIdling()
    }
}