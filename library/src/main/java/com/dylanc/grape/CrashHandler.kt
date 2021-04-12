package com.dylanc.grape

import java.io.File

/**
 * @author Dylan Cai
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

  private var defaultCrashHandler: Thread.UncaughtExceptionHandler? = null
  private lateinit var dirPath: String

  fun init(dirPath: String? = null) {
    defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler(this)
    this.dirPath = dirPath ?: cacheDirPath.orEmpty()
  }

  override fun uncaughtException(t: Thread, e: Throwable) {
    val time = nowLocalDateTime.format("yyyy-MM-dd HH:mm:ss")
    val file = File(dirPath, "crash_${time.replace(" ", "_")}.txt")
    printTo(file) {
      println("Time:          $time")
      println("App version:   $appVersionName ($appVersionCode)")
      println("OS version:    Android $sdkVersionName ($sdkVersionCode)")
      println("Manufacturer:  $deviceManufacturer")
      println("Model:         $deviceModel")
      println()
      e.printStackTrace(this)
    }
    defaultCrashHandler?.uncaughtException(t, e) ?: relaunchApp()
  }
}