@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import java.io.File

/**
 * @author Dylan Cai
 */

inline fun <reified T> Context.intentOf(bundle: Bundle) =
  Intent(this, T::class.java).apply { putExtras(bundle) }

inline fun <reified T> Context.intentOf(vararg pairs: Pair<String, *>) =
  Intent(this, T::class.java).apply { putExtras(bundleOf(*pairs)) }

inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

inline fun Intent.newDocument(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT) }

inline fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

inline fun <reified T> Activity.intentExtras(name: String): Lazy<T?> = lazy {
  intent.extras[name]
}

inline fun <reified T> Activity.intentExtras(name: String, defaultValue: T): Lazy<T> = lazy {
  intent.extras[name] ?: defaultValue
}

inline fun <reified T> Activity.safeIntentExtras(name: String): Lazy<T> = lazy {
  checkNotNull(intent.extras[name]) { "No intent value for key \"$name\"" }
}

inline fun sharePdfIntentOf(pdfFile: File, title: String? = null, sender: IntentSender? = null): Intent {
  val pdfUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    FileProvider.getUriForFile(application, "$packageName.provider", pdfFile)
  } else {
    Uri.fromFile(pdfFile)
  }
  return sharePdfIntentOf(pdfUri, title, sender)
}

inline fun sharePdfIntentOf(pdfUri: Uri, title: String? = null, sender: IntentSender? = null): Intent =
  Intent(Intent.ACTION_SEND).apply {
    type = "application/pdf"
    putExtra(Intent.EXTRA_STREAM, pdfUri)
  }.let {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      Intent.createChooser(it, title, sender)
    } else {
      it
    }
  }