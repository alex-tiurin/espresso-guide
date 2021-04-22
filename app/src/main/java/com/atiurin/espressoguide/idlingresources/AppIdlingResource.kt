package com.atiurin.espressoguide.idlingresources


/*
 * This interface is a representation of Espresso.IdlingResource  interface.
 * It is created to avoid espresso_idling dependency in app
 */
interface AppIdlingResource {
    fun getName(): String
    fun isIdle(): Boolean
    fun registerIdleTransitionCallback(resourceCallback: AppResourceCallback)

    interface AppResourceCallback{
        fun onTransitionToIdleState()
    }
}