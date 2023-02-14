package com.dylanc.longan.sample

import android.app.Application
import com.dylanc.longan.*

/**
 * @author Dylan Cai
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    if (currentProcessName != BuildConfig.APPLICATION_ID) {
      Longan.initialize(this)
    }
    initLogger(BuildConfig.DEBUG)
    saveCrashLogLocally()
    fileProviderAuthority = "$packageName.provider"
  }
}