package com.atiurin.espressoguide.idlingresources.resources

import com.atiurin.espressoguide.idlingresources.AbstractIdlingResource
import com.atiurin.espressoguide.idlingresources.Holder

class ContactsIdlingResource : AbstractIdlingResource(){
    companion object : Holder<ContactsIdlingResource>(::ContactsIdlingResource)
}
