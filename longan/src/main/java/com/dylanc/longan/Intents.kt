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

import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.core.os.bundleOf


inline fun <reified T> Context.intentOf(vararg pairs: Pair<String, *>): Intent =
  intentOf<T>(bundleOf(*pairs))

inline fun <reified T> Context.intentOf(bundle: Bundle): Intent =
  Intent(this, T::class.java).putExtras(bundle)

inline fun <reified T> Activity.intentExtras(name: String) = lazy<T?> {
  intent.extras[name]
}

inline fun <reified T> Activity.intentExtras(name: String, default: T) = lazy {
  intent.extras[name] ?: default
}

inline fun <reified T> Activity.safeIntentExtras(name: String) = lazy<T> {
  checkNotNull(intent.extras[name]) { "No intent value for key \"$name\"" }
}

fun dial(phoneNumber: String): Boolean =
  Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
    .startForActivity()

@RequiresPermission(CALL_PHONE)
fun makeCall(phoneNumber: String): Boolean =
  Intent(Intent.ACTION_CALL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
    .startForActivity()

fun sendSMS(phoneNumber: String, content: String): Boolean =
  Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${Uri.encode(phoneNumber)}"))
    .apply { putExtra("sms_body", content) }
    .startForActivity()

fun browse(url: String, newTask: Boolean = false): Boolean =
  Intent(Intent.ACTION_VIEW, Uri.parse(url))
    .apply { if (newTask) newTask() }
    .startForActivity()

fun email(email: String, subject: String = "", text: String = ""): Boolean =
  Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
    .apply {
      putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
      if (subject.isNotEmpty()) putExtra(Intent.EXTRA_SUBJECT, subject)
      if (text.isNotEmpty()) putExtra(Intent.EXTRA_TEXT, text)
    }
    .startForActivity()

fun installAPK(uri: Uri): Boolean =
  Intent(Intent.ACTION_VIEW)
    .newTask()
    .grantReadUriPermission()
    .apply { setDataAndType(uri, "application/vnd.android.package-archive") }
    .startForActivity()

fun Intent.startForActivity(): Boolean =
  try {
    topActivity.startActivity(this)
    true
  } catch (e: Exception) {
    e.printStackTrace()
    false
  }

fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

fun Intent.newDocument(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT) }

fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

fun Intent.grantReadUriPermission(): Intent = apply {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
  }
}
