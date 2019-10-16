package com.atiurin.espressoguide.data.repositories

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.loaders.MessageLoader


object MessageRepository {
    var messages : HashMap<Int, List<Message>>

    init {
        messages = loadMessages(MessageLoader())
    }

    fun loadMessages(loader: MessageLoader)
            : HashMap<Int, List<Message>>{
        messages = loader.load()
        return messages
    }

    fun searchMessage(contactId: Int, author: Int, recipient: Int, text: String) : Message?{
        return messages[contactId]?.find { it.authorId == author &&  it.receiverId == recipient && it.text == text }
    }

    fun getChatMessages(contactId: Int): ArrayList<Message>{
        val chatMessages = messages[contactId]
        if (chatMessages== null){
            messages[contactId] = ArrayList()
        }
        return messages[contactId] as ArrayList<Message>
    }

    fun clearChatMessages(contactId: Int){
        getChatMessages(contactId).clear()
    }

    fun addChatMessage(contactId: Int, message: Message){
        getChatMessages(contactId).add(message)
    }

    fun getChatMessagesCount(contactId: Int) : Int{
        return getChatMessages(contactId).size
    }


}