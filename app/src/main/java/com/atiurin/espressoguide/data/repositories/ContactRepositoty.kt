package com.atiurin.espressoguide.data.repositories

import android.os.Handler
import com.atiurin.espressoguide.adapters.ContactAdapter
import com.atiurin.espressoguide.data.entities.Contact

object ContactRepositoty {

    fun getContact(id: Int) : Contact{
        return contacts.find { it.id == id }!!
    }
    fun getContact(name: String) : Contact{
        return contacts.find { it.name == name }!!
    }
    private val contacts = CONTACTS
}