package com.atiurin.espressoguide.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.atiurin.espressoguide.adapters.ContactAdapter
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.R
import kotlin.collections.ArrayList
import android.view.View
import android.widget.Toast
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.async.ContactsPresenter
import com.atiurin.espressoguide.async.ContactsProvider
import com.atiurin.espressoguide.data.Tags
import com.atiurin.espressoguide.idlingresources.idling
import com.atiurin.espressoguide.managers.AccountManager
import com.atiurin.espressoguide.view.CircleImageView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ContactsProvider {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ContactAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var accountManager: AccountManager
    private val onItemClickListener: View.OnClickListener? = null
    private val contactsPresenter = ContactsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountManager = AccountManager(applicationContext)
        if (!accountManager.isLoggedIn()) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.title_friends_list)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navigationAvatar = navView.getHeaderView(0).findViewById<CircleImageView>(R.id.navigation_user_avatar)
        navigationAvatar.setOnClickListener {
            startActivity(Intent(applicationContext, ProfileActivity::class.java))
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ContactAdapter(ArrayList<Contact>(),
            object : ContactAdapter.OnItemClickListener {
                override fun onItemClick(contact: Contact) {
                    val intent = Intent(applicationContext, ChatActivity::class.java)
                    intent.putExtra(INTENT_CONTACT_ID_EXTRA_NAME, contact.id)
                    startActivity(intent)
                }
                override fun onItemLongClick(contact: Contact) {
                }
            })
        recyclerView = findViewById<RecyclerView>(R.id.recycler_friends).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.tag = Tags.CONTACTS_LIST
        contactsPresenter.getAllContacts()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(com.atiurin.espressoguide.R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        contactsPresenter.getAllContacts()
    }

    override fun onContactsLoaded(contacts: List<Contact>) {
        viewAdapter.updateData(contacts)
        viewAdapter.notifyDataSetChanged()
        idling { contactsIdling.onDataLoaded() }
    }

    override fun onFailedToLoadContacts(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_settings -> {
                Logger.error("Starting activity SettingsActivity")
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
            }
            R.id.nav_saved_messages -> {
                Snackbar.make(recyclerView, "Saved messages not implemented", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            R.id.nav_profile -> {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }
            R.id.nav_logout -> {
                accountManager.logout()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
