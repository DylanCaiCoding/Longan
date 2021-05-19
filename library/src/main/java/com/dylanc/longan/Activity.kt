@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import java.util.*


internal val activityCache = LinkedList<Activity>()

val activityList: List<Activity> get() = activityCache.toList()

inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, *>, block: Intent.() -> Unit = {}) =
  startActivity(intentOf<T>(*pairs).apply(block))

inline fun startActivity(intent: Intent) = topActivity.run { startActivity(intent) }

inline fun <reified T : Activity> startActivity(vararg pairs: Pair<String, *>, block: Intent.() -> Unit = {}) =
  startActivity(topActivity.intentOf<T>(*pairs).apply(block))

val topActivity: Activity get() = activityCache.last()

inline fun <reified T : Activity> isActivityExistsInStack() = isActivityExistsInStack(T::class.java)

fun <T : Activity> isActivityExistsInStack(clazz: Class<T>) = activityCache.any { it.javaClass == clazz }

inline fun <reified T : Activity> finishActivity() = finishActivity(T::class.java)

fun <T : Activity> finishActivity(clazz: Class<T>) =
  activityCache.removeAll {
    if (it.javaClass == clazz) it.finish()
    it.javaClass == clazz
  }

fun finishAllActivities() =
  activityCache.removeAll {
    it.finish()
    true
  }

inline fun ComponentActivity.pressBackTwiceToExit(toastText: String, delayMillis: Long = 2000) =
  pressBackTwiceToExit(delayMillis) { toast(toastText) }

inline fun ComponentActivity.pressBackTwiceToExit(@StringRes toastText: Int, delayMillis: Long = 2000) =
  pressBackTwiceToExit(delayMillis) { toast(toastText) }

inline fun ComponentActivity.pressBackTwiceToExit(delayMillis: Long = 2000, crossinline onFirstBackPressed: () -> Unit) {
  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    private var lastBackTime: Long = 0

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

inline fun ComponentActivity.pressBackToNotExit() {
  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
      moveTaskToBack(false)
    }
  })
}

inline val Context.context: Context get() = this

inline val Activity.activity: Activity get() = this

inline val FragmentActivity.fragmentActivity: FragmentActivity get() = this

inline val ComponentActivity.lifecycleOwner: LifecycleOwner get() = this

fun checkPermission(permission: String) =
   ActivityCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED

