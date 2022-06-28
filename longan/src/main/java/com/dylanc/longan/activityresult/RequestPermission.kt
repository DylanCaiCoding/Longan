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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dylanc.longan.topActivity

fun ActivityResultCaller.registerForRequestPermissionResult(
  onGranted: () -> Unit,
  onDenied: AppSettingsScope.() -> Unit,
  onShowRequestRationale: PermissionScope.() -> Unit
): ActivityResultLauncher<String> {
  var permissionLauncher: ActivityResultLauncher<String>? = null
  var permission: String? = null
  val launchAppSettingsLauncher = registerForLaunchAppSettingsResult {
    permissionLauncher?.launch(permission)
  }
  permissionLauncher = registerForActivityResult(RequestPermissionContract()) {
    permission = it.first
    when {
      it.second -> onGranted()
      !permission.isNullOrEmpty() && ActivityCompat.shouldShowRequestPermissionRationale(topActivity, permission!!) ->
        onShowRequestRationale(PermissionScope { permissionLauncher?.launch(permission) })
      else -> onDenied(AppSettingsScope { launchAppSettingsLauncher.launch() })
    }
  }
  return permissionLauncher
}

fun ActivityResultCaller.registerForRequestPermissionResult(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

class RequestPermissionContract : ActivityResultContract<String, Pair<String, Boolean>>() {
  private lateinit var permission: String

  override fun createIntent(context: Context, input: String): Intent {
    permission = input
    return Intent(ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS)
      .putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(input))
  }

  override fun parseResult(resultCode: Int, intent: Intent?): Pair<String, Boolean> {
    if (intent == null || resultCode != Activity.RESULT_OK) return permission to false
    val grantResults = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS")
    return permission to if (grantResults == null || grantResults.isEmpty()) false else grantResults[0] == PackageManager.PERMISSION_GRANTED
  }

  override fun getSynchronousResult(context: Context, input: String?): SynchronousResult<Pair<String, Boolean>>? =
    when {
      input == null -> SynchronousResult("" to false)
      ContextCompat.checkSelfPermission(context, input) == PackageManager.PERMISSION_GRANTED -> {
        SynchronousResult(input to true)
      }
      else -> null
    }
}

fun interface PermissionScope {
  fun requestPermissionAgain()
}
