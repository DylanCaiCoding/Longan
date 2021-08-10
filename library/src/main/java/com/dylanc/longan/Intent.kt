@file:Suppress("unused", "NOTHING_TO_INLINE")

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


/**
 * @author Dylan Cai
 */

inline fun Intent(vararg pairs: Pair<String, *>) =
  Intent().apply { putExtras(bundleOf(*pairs)) }

inline fun <reified T> Context.intentOf(vararg pairs: Pair<String, *>) =
  intentOf<T>(bundleOf(*pairs))

inline fun <reified T> Context.intentOf(bundle: Bundle) =
  Intent(this, T::class.java).apply { putExtras(bundle) }

inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

inline fun Intent.newDocument(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT) }

inline fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

inline fun Intent.createChooser(title: String? = null): Intent = Intent.createChooser(this, title)

inline fun Intent.grantReadPermission(): Intent = apply {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
  }
}

inline fun <reified T> Activity.intentExtras(name: String) = lazy<T?> {
  intent.extras[name]
}

inline fun <reified T> Activity.intentExtras(name: String, default: T) = lazy {
  intent.extras[name] ?: default
}

inline fun <reified T> Activity.safeIntentExtras(name: String) = lazy<T> {
  checkNotNull(intent.extras[name]) { "No intent value for key \"$name\"" }
}

fun dial(phoneNumber: String) =
  Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
    .startForActivity()

@RequiresPermission(CALL_PHONE)
inline fun makeCall(phoneNumber: String) =
  Intent(Intent.ACTION_CALL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
    .startForActivity()

inline fun sendSMS(phoneNumber: String, content: String?) =
  Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${Uri.encode(phoneNumber)}"))
    .apply { putExtra("sms_body", content) }
    .startForActivity()

inline fun browse(url: String, newTask: Boolean = false) =
  Intent(Intent.ACTION_VIEW, Uri.parse(url))
    .apply { if (newTask) newTask() }
    .startForActivity()

fun email(email: String, subject: String = "", text: String = "") =
  Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
    .apply {
      putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
      if (subject.isNotEmpty()) putExtra(Intent.EXTRA_SUBJECT, subject)
      if (text.isNotEmpty()) putExtra(Intent.EXTRA_TEXT, text)
    }
    .startForActivity()

inline fun installAPK(uri: Uri) =
  Intent(Intent.ACTION_VIEW)
    .newTask()
    .grantReadPermission()
    .apply { setDataAndType(uri, "application/vnd.android.package-archive") }
    .startForActivity()

inline fun Intent.startForActivity() =
  try {
    topActivity.startActivity(this)
    true
  } catch (e: Exception) {
    e.printStackTrace()
    false
  }