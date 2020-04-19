package com.atiurin.espressoguide.core.settings

data class SettingsItem(val type: SettingsType, val name: String)

enum class SettingsType{
    ACCOUNT,
    PRIVACY,
    NOTIFICATIONS,
    BLACKLIST
}