package com.atiurin.espressoguide.data.repositories

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.loaders.MessageLoader


object MessageRepository {
    var messages : ArrayList<Message>

    init {
        messages = loadMessages(MessageLoader())
    }

    fun loadMessages(loader: MessageLoader)
            : ArrayList<Message>{
        messages = loader.load()
        return messages
    }





    fun getChatMessages(contactId: Int): ArrayList<Message>{
        return ArrayList(messages.filter {message ->
            (message.authorId == contactId && message.receiverId == CURRENT_USER.id) ||
                    (message.authorId == CURRENT_USER.id && message.receiverId == contactId)
        })
    }

    fun clearMessages(){
        messages.clear()
    }

    fun addMessage(message: Message){
        messages.add(message)
    }

    fun getMessagesCount() : Int{
        return messages.size
    }
}