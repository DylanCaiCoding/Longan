@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import java.io.File

/**
 * @author Dylan Cai
 */
inline fun saveCrashLogLocally(dirPath: String = cacheDirPath) {
  handleUncaughtException { thread, e ->
    val time = nowLocalDateTime.format("yyyy-MM-dd HH:mm:ss")
    val file = File(dirPath, "crash_${time.replace(" ", "_")}.txt")
    file.print {
      println("Time:          $time")
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
