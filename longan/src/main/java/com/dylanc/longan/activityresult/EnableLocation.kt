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

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import com.dylanc.longan.isLocationEnabled

fun ActivityResultCaller.registerForEnableLocationResult(
  onLocationEnabled: LocationScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  var enableLocationLauncher: ActivityResultLauncher<Unit>? = null
  enableLocationLauncher = registerForActivityResult(EnableLocationContract()) {
    onLocationEnabled(LocationScope { enableLocationLauncher?.launch() }, it)
  }
  return enableLocationLauncher
}

class EnableLocationContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) = isLocationEnabled

  override fun getSynchronousResult(context: Context, input: Unit?): SynchronousResult<Boolean>? =
    if (isLocationEnabled) SynchronousResult(true) else null
}

fun interface LocationScope {
  fun enableLocation()
}
