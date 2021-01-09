@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent

val activityList = mutableListOf<Activity>()

inline fun <reified T : Activity> Context.startActivity() {
  val intent = Intent(this, T::class.java)
  startActivity(intent)
}

val topActivity: Activity get() = activityList.last()

inline fun <reified T : Activity> isActivityExistsInStack() =
  isActivityExistsInStack(T::class.java)

fun <T : Activity> isActivityExistsInStack(clazz: Class<T>) =
  activityList.any { it.javaClass == clazz }

inline fun <reified T : Activity> finishActivity() = finishActivity(T::class.java)

fun <T : Activity> finishActivity(clazz: Class<T>)  =
  activityList.removeAll {
    if (it.javaClass == clazz) it.finish()
    it.javaClass == clazz
  }

fun finishAllActivities() =
  activityList.removeAll {
    it.finish()
    true
  }