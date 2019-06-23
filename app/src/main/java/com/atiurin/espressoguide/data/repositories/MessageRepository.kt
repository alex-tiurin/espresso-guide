package com.atiurin.espressoguide.data.repositories

import android.os.Handler
import com.atiurin.espressoguide.adapters.MessageAdapter
import com.atiurin.espressoguide.data.entities.Message


object MessageRepository {
    fun getChatMessages(contactId: Int): ArrayList<Message>{
        return ArrayList(messages.filter {message ->
            (message.authorId == contactId && message.receiverId == CURRENT_USER.id) ||
                    (message.authorId == CURRENT_USER.id && message.receiverId == contactId)
        })
    }

     var messages = MESSAGES
}