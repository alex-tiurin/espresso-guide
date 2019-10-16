package com.atiurin.espressoguide.data.loaders

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.repositories.MESSAGES

open class MessageLoader{
    open fun load() : HashMap<Int, List<Message>>{
        return MESSAGES
    }
}