package com.atiurin.espressoguide.data.repositories

import android.os.Handler
import com.atiurin.espressoguide.adapters.ContactAdapter
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.idlingresources.ContactsIdlingResource

object ContactRepositoty {
    fun getAll(adapter: ContactAdapter) {
        ContactsIdlingResource.getInstanceFromApp()?.setIdleState(false)
        Handler().postDelayed({
            adapter.updateData(contacts)
            adapter.notifyDataSetChanged()
            ContactsIdlingResource.getInstanceFromApp()?.setIdleState(true)
        }, 100)
    }

    fun getContact(id: Int) : Contact{
        return contacts.find { it.id == id }!!
    }

    private val contacts = CONTACTS
}