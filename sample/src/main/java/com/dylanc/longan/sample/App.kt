package com.dylanc.longan.sample

import android.app.Application
import com.dylanc.longan.Logger
import com.dylanc.longan.fileProviderAuthority
import com.dylanc.longan.saveCrashLogLocally

/**
 * @author Dylan Cai
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    Logger.init(BuildConfig.DEBUG)
    saveCrashLogLocally()
    fileProviderAuthority = "$packageName.provider"
  }
}