package com.atiurin.espressoguide.idlingresources

import java.util.concurrent.atomic.AtomicReference

fun isReleaseBuild(): Boolean {
    return false
}

interface IdlingScope {
    val contactsIdling: ContactsIdling
    val messagesIdling: MessagesIdling
}

val idlingContainer = AtomicReference<IdlingScope>()

fun idling(action: IdlingScope.() -> Unit){
    if (!isReleaseBuild()){
        idlingContainer.get()?.action()
    }
}
