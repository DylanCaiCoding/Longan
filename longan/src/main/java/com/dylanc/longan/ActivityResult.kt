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

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi


fun ActivityResultLauncher<Unit>.launch() = launch(Unit)

fun ActivityResultCaller.requestPermissionLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

fun ActivityResultCaller.requestMultiplePermissionsLauncher(callback: ActivityResultCallback<Map<String, Boolean>>) =
  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

fun ActivityResultCaller.changeWifiLauncher(callback: ActivityResultCallback<Unit>) =
  ChangeWifiLauncher(this, callback)

@RequiresApi(Build.VERSION_CODES.Q)
fun ActivityResultCaller.openWifiPanelLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(OpenWifiPanelContract(), callback)

fun ActivityResultCaller.launchWifiSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchWifiSettingsContract(), callback)

class ChangeWifiLauncher(caller: ActivityResultCaller, callback: ActivityResultCallback<Unit>) {
  private var catchException = false
  private val launchWifiSettingsLauncher = caller.launchWifiSettingsLauncher(callback)
  private val openWifiPanelLauncher = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    caller.openWifiPanelLauncher { if (!catchException) callback.onActivityResult(Unit) }
  } else {
    null
  }

  fun launch() {
    try {
      catchException = false
      (openWifiPanelLauncher ?: launchWifiSettingsLauncher).launch()
    } catch (e: ActivityNotFoundException) {
      catchException = true
      launchWifiSettingsLauncher.launch()
    }
  }
}

@RequiresApi(Build.VERSION_CODES.Q)
class OpenWifiPanelContract : ActivityResultContract<Unit, Unit>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class LaunchWifiSettingsContract : ActivityResultContract<Unit, Unit>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_WIFI_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}
