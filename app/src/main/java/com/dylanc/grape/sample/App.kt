package com.dylanc.grape.sample

import android.app.Application
import com.dylanc.grape.saveCrashLogLocally

/**
 * @author Dylan Cai
 */
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    saveCrashLogLocally()
  }
}