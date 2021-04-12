@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Application
import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Process
import androidx.core.content.pm.PackageInfoCompat

/**
 * @author Dylan Cai
 */

val application: Application get() = AppInitializer.application

inline val packageName: String get() = application.packageName

inline val activitiesPackageInfo: PackageInfo
  get() = application.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

inline val connectivityManager: ConnectivityManager
  get() = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

inline val appVersionName: String get() = activitiesPackageInfo.versionName

inline val appVersionCode get() = PackageInfoCompat.getLongVersionCode(activitiesPackageInfo)

inline fun relaunchApp(killProcess: Boolean = true) =
  application.packageManager.getLaunchIntentForPackage(packageName)?.let {
    it.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(it)
    if (killProcess) Process.killProcess(Process.myPid())
  }