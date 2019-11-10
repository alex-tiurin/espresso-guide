package com.atiurin.espressoguide.idlingresources
const val RELEASE_BUILD = false;

class IdlingHelper{
    companion object {
        fun ifAllowed(resourceAction:() -> Unit){
            if (!RELEASE_BUILD){
                resourceAction()
            }
        }
    }
}