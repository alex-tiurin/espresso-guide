package com.atiurin.espressoguide.async

import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.repositories.CONTACTS

class GetContacts : UseCase<ArrayList<Contact>, UseCase.None>() {

    override suspend fun run(params: None): Either<Exception, ArrayList<Contact>> {
        return try {
            Thread.sleep(500)
            val contacts = CONTACTS
            Success(contacts)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}