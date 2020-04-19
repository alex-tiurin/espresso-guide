package com.atiurin.espressoguide.data.repositories

import android.os.Handler
import com.atiurin.espressoguide.adapters.ContactAdapter
import com.atiurin.espressoguide.data.entities.Contact

object ContactsRepositoty {
    private val contacts = CONTACTS
    private val blacklist = mutableListOf<Contact>()
    fun getContact(id: Int): Contact {
        return contacts.find { it.id == id }!!
    }

    fun getContact(name: String): Contact {
        return contacts.find { it.name == name }!!
    }

    fun getAllContacts(): ArrayList<Contact> {
        return contacts
    }

    fun getWhiteList(): List<Contact>{
        return contacts - blacklist
    }

    fun addToBlacklist(contactId: Int) {
        blacklist.add(getContact(contactId))
    }

    fun deleteFromBlacklist(contactId: Int) {
        blacklist.remove(getContact(contactId))
    }

    fun getBlacklist(): MutableList<Contact> {
        return blacklist
    }

    fun clearBlacklist(){
        blacklist.clear()
    }
}