package com.atiurin.espressoguide.data

import android.os.Handler
import com.atiurin.espressoguide.RecyclerAdapter
import com.atiurin.espressoguide.idlingresources.ContactsIdlingResource

class ContactRepositoty {
    fun getAll(adapter: RecyclerAdapter){
        ContactsIdlingResource.getInstanceFromApp()?.setIdleState(false)
        Handler().postDelayed({
            adapter.updateData(contacts)
            adapter.notifyDataSetChanged()
            ContactsIdlingResource.getInstanceFromApp()?.setIdleState(true)
        }, 2000)
    }

    private val contacts =  arrayListOf(
            Contact(1, "Chandler Bing", "chandler"),
            Contact(2, "Ross Galler", "ross")
        )
}