package com.atiurin.espressoguide

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.loaders.MessageLoader
import com.atiurin.espressoguide.data.repositories.MessageRepository
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class SimpleUnitTest {
    @Test
    fun testAddNewMessage() {
        val message = Message(1, 2, "new message")
        val initialCount = MessageRepository.getMessagesCount()
        MessageRepository.addMessage(message)
        val actualCount = MessageRepository.getMessagesCount()
        Assert.assertEquals(
            "Expected messages count is ${initialCount + 1} " +
                    "but actual count is $actualCount",
            initialCount + 1, actualCount
        )
    }

    @Test
    fun testClearAllMessages() {
        val message = Message(1, 2, "new message")
        MessageRepository.addMessage(message)
        MessageRepository.clearMessages()
        val actualCount = MessageRepository.getMessagesCount()
        Assert.assertEquals(
            "Expected messages count is 0 but actual count is $actualCount",
            0, actualCount
        )
    }
}