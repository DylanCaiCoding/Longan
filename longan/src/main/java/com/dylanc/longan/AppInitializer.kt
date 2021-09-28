@file:Suppress("unused")

package com.dylanc.longan

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.startup.Initializer

/**
 * @author Dylan Cai
 */

internal class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    application = context as Application
    application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
  }

  override fun dependencies() = emptyList<Class<Initializer<*>>>()

  private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      activityCache.add(activity)
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
      activityCache.remove(activity)
    }
  }

  companion object {
    internal lateinit var application: Application private set
  }
}
