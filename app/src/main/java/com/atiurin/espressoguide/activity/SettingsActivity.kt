package com.atiurin.espressoguide.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.atiurin.espressoguide.Logger
import com.atiurin.espressoguide.R
import com.atiurin.espressoguide.fragment.settings.BlacklistFragment
import com.atiurin.espressoguide.fragment.settings.SettingsFragmentNavigator
import com.atiurin.espressoguide.fragment.settings.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val INTENT_FRAGMENT_CLASS = "INTENT_FRAGMENT_CLASS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.error("SettingsActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val intent = intent
        val fragmentClass = intent.getStringExtra(INTENT_FRAGMENT_CLASS)
            ?: SettingsFragmentNavigator.FragmentType.SETTINGS_LIST.name
        //TOOLBAR
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = getColor(R.color.colorPrimaryDark)
        SettingsFragmentNavigator.init(this)
        val fragmentManager = supportFragmentManager
        val fragment = SettingsFragmentNavigator.getFragment(fragmentClass)
        fragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}