package com.atiurin.espressoguide.fragment.settings


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.atiurin.espressoguide.activity.SettingsActivity

@SuppressLint("StaticFieldLeak")
object SettingsFragmentNavigator {
    lateinit var activity: Activity

    fun init(activity: Activity) {
        this.activity = activity
    }

    fun go(fragmentClass: Class<*>) {
        val intent = Intent(activity, SettingsActivity::class.java)
        intent.putExtra(SettingsActivity.INTENT_FRAGMENT_CLASS, getKey(fragmentClass))
        activity.startActivity(intent)
    }

    fun getKey(fragmentClass: Class<*>): String {
        return when (fragmentClass) {
            BlacklistFragment::class.java -> FragmentType.BLACKLIST.name
            else -> FragmentType.SETTINGS_LIST.name
        }
    }

    fun getFragment(key: String): Fragment {
        return when(FragmentType.valueOf(key)){
            FragmentType.BLACKLIST -> BlacklistFragment(activity)
            else -> SettingsFragment(activity)
        }
    }

    enum class FragmentType{
        BLACKLIST, SETTINGS_LIST
    }
}

