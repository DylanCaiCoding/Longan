@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import java.io.File


/**
 * @author Dylan Cai
 */

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

inline fun Intent.createChooser(title: String? = null): Intent =
  Intent.createChooser(this, title)

inline fun <reified T> Activity.intentExtras(name: String): Lazy<T?> = lazy {
  intent.extras[name]
}

inline fun <reified T> Activity.intentExtras(name: String, defaultValue: T): Lazy<T> = lazy {
  intent.extras[name] ?: defaultValue
}

inline fun <reified T> Activity.safeIntentExtras(name: String): Lazy<T> = lazy {
  checkNotNull(intent.extras[name]) { "No intent value for key \"$name\"" }
}

inline val sendIntent get() = Intent(Intent.ACTION_SEND)

inline fun shareTextIntentOf(content: String, title: String? = null) =
  sendIntent.run {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, content)
    createChooser(title)
  }

inline fun shareImageIntentOf(imageUri: Uri, title: String? = null) =
  shareTextImageIntentOf(null, imageUri, title)

inline fun shareTextImageIntentOf(content: String?, imageUri: Uri, title: String? = null) =
  sendIntent.run {
    type = "image/*"
    putExtra(Intent.EXTRA_TEXT, content)
    putExtra(Intent.EXTRA_STREAM, imageUri)
    createChooser(title)
  }

inline fun shareTextImageIntentOf(content: String?, uris: ArrayList<Uri>, title: String? = null) =
  sendIntent.run {
    type = "image/*"
    putExtra(Intent.EXTRA_TEXT, content)
    putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
    createChooser(title)
  }

inline fun sharePdfIntentOf(pdfFile: File, title: String? = null) =
  sharePdfIntentOf(pdfFile.toUri(), title)

inline fun sharePdfIntentOf(pdfUri: Uri, title: String? = null) =
  sendIntent.run {
    type = "application/pdf"
    putExtra(Intent.EXTRA_STREAM, pdfUri)
    createChooser(title)
  }
