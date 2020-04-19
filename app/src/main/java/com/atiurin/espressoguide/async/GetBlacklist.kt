package com.atiurin.espressoguide.async

import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import kotlinx.coroutines.delay

class GetBlacklist: UseCase<List<Contact>, UseCase.None>() {
    override suspend fun run(params: None): Either<Exception, List<Contact>> {
        return try {
            delay(200)
            val contacts = ContactsRepositoty.getBlacklist()
            Success(contacts)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}