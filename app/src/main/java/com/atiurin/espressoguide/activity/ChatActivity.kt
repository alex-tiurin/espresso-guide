package com.atiurin.espressoguide.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.atiurin.espressoguide.R

class ChatActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_chat)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Chat act")
        setSupportActionBar(toolbar)
    }
}