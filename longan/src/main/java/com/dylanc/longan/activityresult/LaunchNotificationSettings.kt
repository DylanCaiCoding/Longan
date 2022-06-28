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

package com.dylanc.longan.activityresult

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract

fun ActivityResultCaller.registerForLaunchNotificationSettingsResult(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchNotificationSettingsContract(), callback)

class LaunchNotificationSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    } else {
      Intent("android.settings.APP_NOTIFICATION_SETTINGS")
        .putExtra("app_package", context.packageName)
        .putExtra("app_uid", context.applicationInfo.uid)
    }

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}
