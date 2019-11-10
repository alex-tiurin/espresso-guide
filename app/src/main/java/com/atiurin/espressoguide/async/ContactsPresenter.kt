package com.atiurin.espressoguide.async

import androidx.test.espresso.IdlingResource
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.idlingresources.AbstractIdlingResource
import com.atiurin.espressoguide.idlingresources.IdlingHelper
import com.atiurin.espressoguide.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressoguide.idlingresources.resources.ContactsIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ContactsPresenter <T : ContactsProvider>(
    private val executor: T,
    private val coroutineContext: CoroutineContext = Dispatchers.Default) {

    protected lateinit var scope: PresenterCoroutineScope

    fun getAllContacts() {
        IdlingHelper.ifAllowed {
            ContactsIdlingResource.getInstanceFromApp()
                ?.setIdleState(false)
        }

        scope = PresenterCoroutineScope(coroutineContext)
        scope.launch {
            GetContacts()(
                UseCase.None,
                onSuccess = { executor.onContactsLoaded(it) },
                onFailure = { executor.onFailedToLoadContacts(it.message) }
            )
        }
    }
}

class PresenterCoroutineScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context + Job()
}

interface ContactsProvider {
    fun onContactsLoaded(contacts: ArrayList<Contact>)
    fun onFailedToLoadContacts(message: String?)
}