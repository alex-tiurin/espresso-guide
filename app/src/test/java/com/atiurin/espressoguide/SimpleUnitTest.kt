package com.atiurin.espressoguide

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.repositories.MessageRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SimpleUnitTest {

    @Before
    fun clearRepository(){
        MessageRepository.clearMessages()
    }
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
    fun testSearchAddedMessage() {
        val message = Message(1, 3, "add message")
        MessageRepository.addMessage(message)
        val searchedMessage = MessageRepository.searchMessage(message.authorId, message.receiverId, message.text)
        Assert.assertEquals("Expected message aren't the same as found one", message, searchedMessage)
    }

    @Test
    fun testSearchRightMessage() {
        val expectedMessage = Message(1, 3, "add message")
        val message2 = Message(2, 3, "another message")
        MessageRepository.addMessage(expectedMessage)
        MessageRepository.addMessage(message2)
        val searchedMessage = MessageRepository.searchMessage(
            expectedMessage.authorId,
            expectedMessage.receiverId,
            expectedMessage.text)
        Assert.assertEquals("Expected message aren't the same as found one", expectedMessage, searchedMessage)
    }

    @Test
    fun testDemoAssertionFailed() {
        val expectedMessage = Message(1, 3, "add message")
        val message2 = Message(2, 3, "another message")
        MessageRepository.addMessage(expectedMessage)
        MessageRepository.addMessage(message2)
        val searchedMessage = MessageRepository.searchMessage(
            expectedMessage.authorId,
            expectedMessage.receiverId,
            expectedMessage.text)
        Assert.assertEquals("Expected message aren't the same as found one", expectedMessage, message2)
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