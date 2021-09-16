@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Dylan Cai
 */
inline fun saveCrashLogLocally(dirPath: String = cacheDirPath) {
  handleUncaughtException { thread, e ->
    val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    val file = File(dirPath, "crash_${dateTime.replace(" ", "_")}.txt")
    file.print {
      println("Time:          $dateTime")
      println("App version:   $appVersionName ($appVersionCode)")
      println("OS version:    Android $sdkVersionName ($sdkVersionCode)")
      println("Manufacturer:  $deviceManufacturer")
      println("Model:         $deviceModel")
      println("Thread:        ${thread.name}")
      println()
      e.printStackTrace(this)
    }
  }
}

inline fun handleUncaughtException(crossinline block: (Thread, Throwable) -> Unit) {
  val defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
  Thread.setDefaultUncaughtExceptionHandler { t, e ->
    block(t, e)
    defaultCrashHandler?.uncaughtException(t, e)
  }
}
