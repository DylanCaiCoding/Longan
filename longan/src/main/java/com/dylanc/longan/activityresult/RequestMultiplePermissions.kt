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

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.app.ActivityCompat
import com.dylanc.longan.topActivity

fun ActivityResultCaller.registerForRequestMultiplePermissionsResult(
  onAllGranted: () -> Unit,
  onDenied: AppSettingsScope.(List<String>) -> Unit,
  onShowRequestRationale: PermissionsScope.(List<String>) -> Unit
): ActivityResultLauncher<Array<String>> {
  var permissionsLauncher: ActivityResultLauncher<Array<String>>? = null
  val deniedList = mutableListOf<String>()
  val launchAppSettingsLauncher = registerForLaunchAppSettingsResult {
    permissionsLauncher?.launch(deniedList.toTypedArray())
  }
  permissionsLauncher = registerForRequestMultiplePermissionsResult { result ->
    if (result.containsValue(false)) {
      deniedList.clear()
      deniedList.addAll(result.asIterable().filter { !it.value }.map { it.key })
      val explainableList = deniedList.filter {
        ActivityCompat.shouldShowRequestPermissionRationale(topActivity, it)
      }
      if (explainableList.isNotEmpty()) {
        onShowRequestRationale(PermissionsScope {
          permissionsLauncher?.launch(explainableList.toTypedArray())
        }, explainableList)
      } else {
        onDenied(AppSettingsScope { launchAppSettingsLauncher.launch() }, deniedList)
      }
    } else {
      onAllGranted()
    }
  }
  return permissionsLauncher
}

fun ActivityResultCaller.registerForRequestMultiplePermissionsResult(callback: ActivityResultCallback<Map<String, Boolean>>) =
  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

fun interface PermissionsScope {
  fun requestDeniedPermissions()
}
