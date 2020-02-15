package com.atiurin.espressoguide

import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.repositories.MessageRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class SimpleUnitTest {

    @Before
    fun clearRepository(){
        MessageRepository.clearChatMessages(2)
    }

    @Ignore
    @Test
    fun testAddNewMessage() {
        val message = Message(1, 2, "new message")
        val initialCount = MessageRepository.getChatMessagesCount(2)
        MessageRepository.addChatMessage(2,message)
        val actualCount = MessageRepository.getChatMessagesCount(2)
        Assert.assertEquals(
            "Expected messages count is ${initialCount + 1} " +
                    "but actual count is $actualCount",
            initialCount + 1, actualCount
        )
    }

    @Ignore
    @Test
    fun testSearchAddedMessage() {
        val message = Message(1, 3, "add message")
        MessageRepository.addChatMessage(2, message)
        val searchedMessage = MessageRepository.searchMessage(message.authorId, message.receiverId,2, message.text)
        Assert.assertEquals("Expected message aren't the same as found one", message, searchedMessage)
    }

    @Ignore
    @Test
    fun testSearchRightMessage() {
        val expectedMessage = Message(1, 3, "add message")
        val message2 = Message(2, 3, "another message")
        MessageRepository.addChatMessage(2, expectedMessage)
        MessageRepository.addChatMessage(2, message2)
        val searchedMessage = MessageRepository.searchMessage(
            expectedMessage.authorId,
            expectedMessage.receiverId,2,
            expectedMessage.text)
        Assert.assertEquals("Expected message aren't the same as found one", expectedMessage, searchedMessage)
    }

    @Ignore
    @Test
    fun testDemoAssertionFailed() {
        val expectedMessage = Message(1, 3, "add message")
        val message2 = Message(2, 3, "another message")
        MessageRepository.addChatMessage(2, expectedMessage)
        MessageRepository.addChatMessage(2, message2)
        val searchedMessage = MessageRepository.searchMessage(
            expectedMessage.authorId,
            expectedMessage.receiverId,2,
            expectedMessage.text)
        Assert.assertEquals("Expected message aren't the same as found one", expectedMessage, message2)
    }

    @Ignore
    @Test
    fun testClearAllMessages() {
        val message = Message(1, 2, "new message")
        MessageRepository.addChatMessage(2, message)
        MessageRepository.clearChatMessages(2)
        val actualCount = MessageRepository.getChatMessagesCount(2)
        Assert.assertEquals(
            "Expected messages count is 0 but actual count is $actualCount",
            0, actualCount
        )
    }
}