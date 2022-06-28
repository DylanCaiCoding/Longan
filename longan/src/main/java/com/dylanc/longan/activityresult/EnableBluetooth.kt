/*
 * Copyright (C) 2021. Dylan Cai
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

package com.dylanc.longan.activityresult

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.annotation.RequiresPermission
import com.dylanc.longan.isBluetoothEnabled

@RequiresPermission(Manifest.permission.BLUETOOTH)
fun ActivityResultCaller.registerForEnableBluetoothResult(
  onLocationDisabled: LocationScope.() -> Unit,
  onBluetoothEnabled: BluetoothScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  val enableBluetoothLauncher = registerForEnableBluetoothResult(onBluetoothEnabled)
  val enableLocationLauncher = registerForEnableLocationResult { enabled ->
    if (enabled) {
      enableBluetoothLauncher.launch()
    } else {
      onLocationDisabled(this)
    }
  }
  return enableLocationLauncher
}

@RequiresPermission(Manifest.permission.BLUETOOTH)
fun ActivityResultCaller.registerForEnableBluetoothResult(
  onBluetoothEnabled: BluetoothScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  var enableBluetoothLauncher: ActivityResultLauncher<Unit>? = null
  enableBluetoothLauncher = registerForActivityResult(EnableBluetoothContract()) {
    onBluetoothEnabled(BluetoothScope { enableBluetoothLauncher?.launch() }, it)
  }
  return enableBluetoothLauncher
}

class EnableBluetoothContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    resultCode == Activity.RESULT_OK

  override fun getSynchronousResult(context: Context, input: Unit?): SynchronousResult<Boolean>? =
    if (isBluetoothEnabled) SynchronousResult(true) else null
}

fun interface BluetoothScope {
  fun enableBluetooth()
}
