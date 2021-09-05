@file:Suppress("unused", "ObjectPropertyName")

package com.dylanc.longan

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.startup.Initializer
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * @author Dylan Cai
 */

internal class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    application = context as Application
    application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    AndroidThreeTen.init(application)
  }

  override fun dependencies() = emptyList<Class<Initializer<*>>>()

  private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      activityCache.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
      activityCache.remove(activity)
    }
  }

  companion object {
    internal lateinit var application: Application private set
  }
}
