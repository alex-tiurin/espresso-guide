package com.atiurin.espressoguide.framework

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressoguide.Logger

open class CustomActivityTestRule<T : Activity> : ActivityTestRule<T> {
    constructor(activityClass: Class<T>) : super(activityClass)
    constructor(activityClass: Class<T>, initialTouchMode: Boolean, launchActivity: Boolean) : super(activityClass, initialTouchMode, launchActivity)

    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        Logger.life("beforeActivityLaunched")
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        Logger.life("afterActivityLaunched")
    }

    override fun finishActivity() {
        super.finishActivity()
        Logger.life("finishActivity")
    }
}