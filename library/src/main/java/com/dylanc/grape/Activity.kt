@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.content.Context

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