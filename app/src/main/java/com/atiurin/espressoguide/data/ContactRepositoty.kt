package com.atiurin.espressoguide.data

class ContactRepositoty {
    fun getAll() : ArrayList<Contact>{
        return arrayListOf(
            Contact(1, "Chandler Bing", "chandler"),
            Contact(2, "Ross Galler", "ross")

        )
    }
}