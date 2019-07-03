package com.atiurin.espressoguide.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.view.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(){
     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
         super.onCreate(savedInstanceState, persistentState)
         setContentView(R.layout.activity_profile)
         val avatar = findViewById<CircleImageView>(R.id.avatar)
         avatar.setImageDrawable(getDrawable(CURRENT_USER.avatar))
         val name = findViewById<EditText>(R.id.et_username)
         name.hint = CURRENT_USER.name
     }
}