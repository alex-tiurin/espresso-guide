package com.atiurin.espressoguide.async

import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import kotlinx.coroutines.delay

class GetContacts : UseCase<List<Contact>, UseCase.None>() {
    override suspend fun run(params: None): Either<Exception, List<Contact>> {
        return try {
            delay(200)
            val contacts = ContactsRepositoty.getWhiteList()
            Success(contacts)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}