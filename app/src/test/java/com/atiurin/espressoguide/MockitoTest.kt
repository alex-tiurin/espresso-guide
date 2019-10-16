package com.atiurin.espressoguide

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.loaders.MessageLoader
import com.atiurin.espressoguide.data.repositories.MessageRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class MockitoTest{
    @Test
    fun testWithMockLoader(){
        val messages = ArrayList<Message>()
        messages.add(Message(1, 2, "new message"))
        val mockLoader = Mockito.mock(MessageLoader::class.java)
        Mockito.`when`(mockLoader.load()).thenReturn(messages)
        MessageRepository.loadMessages(mockLoader)
        val actualCount = MessageRepository.getChatMessagesCount()
        Assert.assertEquals("Expected messages count is 1 " +
                "but actual count is $actualCount",
            1, actualCount)
    }
}