package com.atiurin.espressoguide.idlingresources

import androidx.annotation.VisibleForTesting

@VisibleForTesting
class ContactsIdlingResource : AbstractIdlingResource(){
    companion object : SingletonHolder<ContactsIdlingResource>(::ContactsIdlingResource)
}