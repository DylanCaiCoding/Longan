@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes


val activityList = mutableListOf<Activity>()

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, *>) =
  intentOf<T>(*pairs).also { startActivity(it) }

val topActivity: Activity get() = activityList.last()

inline fun <reified T : Activity> isActivityExistsInStack() = isActivityExistsInStack(T::class.java)

fun <T : Activity> isActivityExistsInStack(clazz: Class<T>) = activityList.any { it.javaClass == clazz }

inline fun <reified T : Activity> finishActivity() = finishActivity(T::class.java)

fun <T : Activity> finishActivity(clazz: Class<T>) =
  activityList.removeAll {
    if (it.javaClass == clazz) it.finish()
    it.javaClass == clazz
  }

fun finishAllActivities() =
  activityList.removeAll {
    it.finish()
    true
  }

private var lastBackTime: Long = 0

fun ComponentActivity.exitAfterBackPressedTwice(toastText: String, delayMillis: Long = 2000) =
  exitAfterBackPressedTwice(delayMillis) { toast(toastText) }

fun ComponentActivity.exitAfterBackPressedTwice(@StringRes toastText: Int, delayMillis: Long = 2000) =
  exitAfterBackPressedTwice(delayMillis) { toast(toastText) }

fun ComponentActivity.exitAfterBackPressedTwice(delayMillis: Long = 2000, onFirstBackPressed: () -> Unit) {
  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
      val currentTime = System.currentTimeMillis()
      if (currentTime - lastBackTime > delayMillis) {
        onFirstBackPressed()
        lastBackTime = currentTime
      } else {
        finishAllActivities()
      }
    }
  })
}
