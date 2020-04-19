package com.atiurin.espressoguide.fragment.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.adapters.SettingsAdapter
import com.atiurin.espressoguide.core.settings.SettingsItem
import com.atiurin.espressoguide.core.settings.SettingsType
import com.google.android.material.snackbar.Snackbar

class SettingsFragment(
    val activity: Activity
) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SettingsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentInfoContainer.currentFragment = this.javaClass.name
        val rootView = inflater.inflate(R.layout.fragment_settings_list, container, false)
        val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Settings"
        viewManager = LinearLayoutManager(activity)
        viewAdapter = SettingsAdapter(
            listOf(
                SettingsItem(SettingsType.ACCOUNT, "Account"),
                SettingsItem(SettingsType.PRIVACY, "Privacy"),
                SettingsItem(SettingsType.NOTIFICATIONS, "Notifications"),
                SettingsItem(SettingsType.BLACKLIST, "Blacklist")
            ),
            object : SettingsAdapter.OnItemClickListener {
                override fun onItemClick(item: SettingsItem) {
                    Logger.debug("Click on item ${item.name}")
                    when (item.type) {
                        SettingsType.BLACKLIST -> SettingsFragmentNavigator.go(BlacklistFragment::class.java)
                        else -> {
                            Snackbar.make(recyclerView, "This option is not implemented", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()
                        }
                    }
                }
            })
        recyclerView = rootView.findViewById<RecyclerView>(R.id.settings_list).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return rootView
    }
}