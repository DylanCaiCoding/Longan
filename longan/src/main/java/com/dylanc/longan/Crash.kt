/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.os.Looper
import java.io.File
import java.time.Instant

inline fun handleUncaughtException(crossinline block: (Thread, Throwable) -> Unit) {
  val defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
  Thread.setDefaultUncaughtExceptionHandler { t, e ->
    block(t, e)
    defaultCrashHandler?.uncaughtException(t, e)
  }
}

inline fun handleMainThreadException(crossinline block: (Throwable) -> Unit) {
  mainThreadHandler.post {
    while (true) {
      try {
        Looper.loop()
      } catch (e: Throwable) {
        block(e)
      }
    }
  }
}

fun Context.saveCrashLogLocally(dirPath: String = cacheDirPath) =
  handleUncaughtException { thread, e ->
    val now = Instant.now()
    File(dirPath, "crash_${now.format("yyyy-MM-dd")}.txt").print(append = true) {
      println("Time:          ${now.format("yyyy-MM-dd HH:mm:ss")}")
      println("App version:   $appVersionName ($appVersionCode)")
      println("OS version:    Android $sdkVersionName ($sdkVersionCode)")
      println("Manufacturer:  $deviceManufacturer")
      println("Model:         $deviceModel")
      println("Thread:        ${thread.name}")
      println()
      e.printStackTrace(this)
      println()
      println("-----------------------------------------------------")
      println()
    }
  }
