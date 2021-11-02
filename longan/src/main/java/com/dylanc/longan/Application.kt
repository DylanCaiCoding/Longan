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

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.os.Process
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat


val application: Application get() = AppInitializer.application

inline val packageName: String get() = application.packageName

inline val activitiesPackageInfo: PackageInfo
  get() = application.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)

inline val appName: String
  get() = application.applicationInfo.loadLabel(application.packageManager).toString()

inline val appVersionName: String get() = activitiesPackageInfo.versionName

inline val appVersionCode: Long
  get() = PackageInfoCompat.getLongVersionCode(activitiesPackageInfo)

inline val isAppDebug: Boolean
  get() = application.packageManager.getApplicationInfo(packageName, 0).flags and
      ApplicationInfo.FLAG_DEBUGGABLE != 0

inline val isAppDarkMode: Boolean
  get() = (application.resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES

fun launchAppDetailsSettings(): Boolean =
  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    .apply { data = Uri.fromParts("package", packageName, null) }
    .startForActivity()

fun relaunchApp(killProcess: Boolean = true) =
  application.packageManager.getLaunchIntentForPackage(packageName)?.let {
    it.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(it)
    if (killProcess) Process.killProcess(Process.myPid())
  }
