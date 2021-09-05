package com.dylanc.longan.sample

import android.app.Application
import com.dylanc.longan.fileProviderAuthority
import com.dylanc.longan.initLogger
import com.dylanc.longan.saveCrashLogLocally

/**
 * @author Dylan Cai
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    initLogger(BuildConfig.DEBUG)
    saveCrashLogLocally()
    fileProviderAuthority = "$packageName.provider"
  }
}