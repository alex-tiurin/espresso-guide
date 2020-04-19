package com.atiurin.espressoguide.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.view.CircleImageView
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.adapters.MessageAdapter
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.entities.Message
import com.atiurin.espressoguide.data.repositories.CURRENT_USER
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.data.repositories.MessageRepository
import com.atiurin.espressoguide.managers.AccountManager
import com.google.android.material.snackbar.Snackbar

const val INTENT_CONTACT_ID_EXTRA_NAME = "contactId"

class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var contact: Contact
    private val onItemClickListener: View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accountManager = AccountManager(applicationContext)
        if (!accountManager.isLogedIn()) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_chat)
        val context = this
        //TOOLBAR
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = getColor(R.color.colorPrimaryDark)
        val intent = intent
        val contactId = intent.getIntExtra(INTENT_CONTACT_ID_EXTRA_NAME, -1)
        val title = findViewById<TextView>(R.id.toolbar_title)
        if (contactId < 0) {
            Log.d("EspressoGuide", "Something goes wrong!")
        }
        contact = ContactsRepositoty.getContact(contactId)
        title.text = contact.name
        val avatar = findViewById<CircleImageView>(R.id.toolbar_avatar)
        avatar.setImageDrawable(getDrawable(contact.avatar))
        //message input area
        val messageInput = findViewById<EditText>(R.id.message_input_text)
        val sendBtn = findViewById<ImageView>(R.id.send_button)
        val attachBtn = findViewById<ImageView>(R.id.attach_button)

        //recycler view and adapter
        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(
            ArrayList(),
            object : MessageAdapter.OnItemClickListener {
                override fun onItemClick(message: Message) {
                    Log.w("EspressoGuid", "Clicked message ${message.text}")
                }

                override fun onItemLongClick(message: Message) {
                    Log.w("EspressoGuid", "Long Clicked message ${message.text}")
                }
            })
        recyclerView = findViewById<RecyclerView>(R.id.messages_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        sendBtn.setOnClickListener {
            if (messageInput.text.isEmpty()) {
                Toast.makeText(context, "Type message text", Toast.LENGTH_LONG).show()
            } else {
                val mes = Message(CURRENT_USER.id, contactId, messageInput.text.toString())
                val chatMessages = MessageRepository.getChatMessages(contactId)
                chatMessages.add(mes)
                updateAdapter(chatMessages)
                messageInput.setText("")
                recyclerView.smoothScrollToPosition(viewAdapter.itemCount - 1)
            }
        }
        attachBtn.setOnClickListener{
            Snackbar.make(recyclerView, "Attach not implemented", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                MessageRepository.clearChatMessages(contact.id)
                updateAdapter(ArrayList())
                true
            }
            R.id.action_add_blacklist -> {
                ContactsRepositoty.addToBlacklist(contact.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewAdapter.updateData(MessageRepository.getChatMessages(contact.id))
        viewAdapter.notifyDataSetChanged()
    }

    private fun updateAdapter(list: MutableList<Message>) {
        viewAdapter.updateData(list)
        viewAdapter.notifyDataSetChanged()
    }
}