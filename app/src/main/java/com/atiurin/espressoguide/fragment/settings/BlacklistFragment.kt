package com.atiurin.espressoguide.fragment.settings

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.adapters.ContactAdapter
import com.atiurin.espressoguide.async.ContactsPresenter
import com.atiurin.espressoguide.async.ContactsProvider
import com.atiurin.espressoguide.data.entities.Contact
import com.atiurin.espressoguide.data.repositories.ContactsRepositoty
import com.atiurin.espressoguide.idlingresources.idling

class BlacklistFragment(
    val activity: Activity
) : Fragment(), ContactsProvider {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ContactAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val contactsPresenter = ContactsPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Blacklist"

        FragmentInfoContainer.currentFragment = this.javaClass.name
        val rootView = inflater.inflate(R.layout.fragment_blacklist, container, false)
        viewManager = LinearLayoutManager(activity)
        viewAdapter = ContactAdapter(
            emptyList(),
            object : ContactAdapter.OnItemClickListener {
                override fun onItemClick(contact: Contact) {
                    Logger.debug("Click on item ${contact.name}")

                }

                override fun onItemLongClick(contact: Contact) {
                    Logger.debug("Long Click on item ${contact.name}")

                    val pop = PopupMenu(activity, viewAdapter.getContactView(contact))
                    pop.menu.add("Delete ${contact.name} from blacklist")
                    pop.setOnMenuItemClickListener { item ->
                        Logger.debug("MenuClicked on item ${contact.name}")
                        ContactsRepositoty.deleteFromBlacklist(contact.id)
                        contactsPresenter.getBlacklist()
                        true
                    }
                    pop.show()
                    true
                }
            })
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_blacklist).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        registerForContextMenu(recyclerView)
        contactsPresenter.getBlacklist()
        return rootView
    }

    override fun onContactsLoaded(contacts: List<Contact>) {
        viewAdapter.updateData(contacts)
        viewAdapter.notifyDataSetChanged()
        idling { contactsIdling.setIdleState(true) }
    }

    override fun onFailedToLoadContacts(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)

    }
}