package com.atiurin.espressoguide.idlingresources.resources

import com.atiurin.espressoguide.idlingresources.AbstractIdlingResource
import com.atiurin.espressoguide.idlingresources.Holder

class ChatIdlingResource : AbstractIdlingResource(){
    companion object : Holder<ChatIdlingResource>(::ChatIdlingResource)
}