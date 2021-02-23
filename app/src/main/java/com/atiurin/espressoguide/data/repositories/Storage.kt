package com.atiurin.espressoguide.data.repositories

import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.entities.User

val CURRENT_USER = User(1, "Joey Tribbiani", Avatars.JOEY.drawable, "joey", "1234")

val CONTACTS = arrayListOf(
    Contact(2, "Chandler Bing", "Joey doesn't share food!", Avatars.CHANDLER.drawable),
    Contact(3, "Ross Geller", "UNAGI", Avatars.ROSS.drawable),
    Contact(4, "Rachel Green", "I got off the plane!", Avatars.RACHEL.drawable),
    Contact(5, "Phoebe Buffay", "Smelly cat, smelly cat..", Avatars.PHOEBE.drawable),
    Contact(6, "Monica Geller", "I need to clean up", Avatars.MONICA.drawable),
    Contact(7, "Gunther", "They were on break :(", Avatars.GUNTHER.drawable),
    Contact(8, "Janice", "Oh. My. God", Avatars.JANICE.drawable),
    Contact(9, "Bob", "I wanna drink", Avatars.DEFAULT.drawable),
    Contact(10, "Marty McFly", "Back to the ...", Avatars.DEFAULT.drawable),
    Contact(12, "Emmet Brown", "Time fluid capacitor", Avatars.DEFAULT.drawable),
    Contact(13, "Friend_1", "I'm a friend", Avatars.DEFAULT.drawable),
    Contact(14, "Friend_2", "I'm a friend", Avatars.DEFAULT.drawable),
    Contact(15, "Friend_3", "I'm a friend", Avatars.DEFAULT.drawable),
    Contact(16, "Friend_4", "I'm a friend", Avatars.DEFAULT.drawable),
    Contact(17, "Friend_5", "I'm a friend", Avatars.DEFAULT.drawable)
)

enum class Avatars(val drawable: Int) {
    CHANDLER(R.drawable.chandler),
    ROSS(R.drawable.ross),
    MONICA(R.drawable.monica),
    RACHEL(R.drawable.rachel),
    PHOEBE(R.drawable.phoebe),
    GUNTHER(R.drawable.gunther),
    JOEY(R.drawable.joey),
    JANICE(R.drawable.janice),
    DEFAULT(R.drawable.default_avatar)
}


val MESSAGES = hashMapOf<Int, List<Message>>(
    2 to arrayListOf(
        Message(1, 2, "What's up Chandler"),
        Message(2, 1, "Hi Joey"),
        Message(1, 2, "Let's drink coffee"),
        Message(2, 1, "Ok")
    ),
    3 to arrayListOf(
        Message(1, 3, "Do u wanna coffee?"),
        Message(3, 1, "yep, let's go")
    ),
    8 to arrayListOf(
        Message(8, 1, "We are neighbours!"),
        Message(1, 8, "oh my God")
    )
)