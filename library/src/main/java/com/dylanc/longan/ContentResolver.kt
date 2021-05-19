@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.app.RecoverableSecurityException
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresPermission


/**
 * @author Dylan Cai
 */


inline val contentResolver: ContentResolver get() = application.contentResolver

inline fun <R> ContentResolver.query(
  uri: Uri,
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
) =
  query(uri, projection, selection, selectionArgs, sortOrder)?.use(block)

inline fun ContentResolver.update(@RequiresPermission.Write uri: Uri, values: ContentValues? = null, extras: Bundle? = null) =
  update(uri, values, extras)

inline fun ContentResolver.update(@RequiresPermission.Write uri: Uri, extras: Bundle? = null, block: ContentValues.() -> Unit) =
  update(uri, contentValues(block), extras)

inline fun ContentResolver.update(
  @RequiresPermission.Write uri: Uri,
  values: ContentValues? = null,
  where: String? = null,
  selectionArgs: Array<String>? = null
) =
  update(uri, values, where, selectionArgs)

inline fun ContentResolver.update(
  @RequiresPermission.Write uri: Uri,
  where: String? = null,
  selectionArgs: Array<String>? = null,
  block: ContentValues.() -> Unit
) =
  update(uri, contentValues(block), where, selectionArgs)

inline fun ContentResolver.delete(@RequiresPermission.Write uri: Uri, extras: Bundle? = null) =
  delete(uri, extras)

inline fun ContentResolver.delete(@RequiresPermission.Write uri: Uri, where: String? = null, selectionArgs: Array<String>? = null) =
  delete(uri, where, selectionArgs)

inline fun Activity.delete(contentUri: Uri, id: Long = ContentUris.parseId(contentUri)) {
  val requestCode = 100
  when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
      val pendingIntent = MediaStore.createDeleteRequest(contentResolver, listOf(contentUri))
      activity.startIntentSenderForResult(
        pendingIntent.intentSender, requestCode, null,
        0, 0, 0, null
      )
    }
    Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
      try {
        val selection = "${MediaStore.MediaColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        contentResolver.delete(contentUri, selection, selectionArgs)
      } catch (ex: RecoverableSecurityException) {
        val intent = ex.userAction.actionIntent.intentSender
        activity.startIntentSenderForResult(
          intent, requestCode, null,
          0, 0, 0, null
        )
      }
    }
    else -> {
      val selection = "${MediaStore.MediaColumns._ID} = ?"
      val selectionArgs = arrayOf(id.toString())
      contentResolver.delete(contentUri, selection, selectionArgs)
    }
  }
}

inline fun ContentResolver.registerContentObserver(uri: Uri, crossinline observer: (Boolean) -> Unit) =
  object : ContentObserver(mainHandler.value) {
    override fun onChange(selfChange: Boolean) {
      observer(selfChange)
    }
  }.also {
    registerContentObserver(uri, true, it)
  }

inline fun contentValues(block: ContentValues.() -> Unit) = ContentValues().apply(block)

inline var ContentValues.displayName: String?
  get() = get(MediaStore.MediaColumns.DISPLAY_NAME) as String?
  set(value) {
    put(MediaStore.MediaColumns.DISPLAY_NAME, value)
  }

inline var ContentValues.relativePath: String?
  get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    get(MediaStore.MediaColumns.RELATIVE_PATH) as String?
  } else {
    null
  }
  set(value) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      put(MediaStore.MediaColumns.RELATIVE_PATH, value)
    }
  }

inline var ContentValues.mimeType: String?
  get() = get(MediaStore.MediaColumns.MIME_TYPE) as String?
  set(value) {
    put(MediaStore.MediaColumns.MIME_TYPE, value)
  }

inline var ContentValues.dateAdded: Long?
  get() = get(MediaStore.MediaColumns.DATE_ADDED) as Long?
  set(value) {
    put(MediaStore.MediaColumns.DATE_ADDED, value)
  }

inline var ContentValues.dateModified: Long?
  get() = get(MediaStore.MediaColumns.DATE_MODIFIED) as Long?
  set(value) {
    put(MediaStore.MediaColumns.DATE_MODIFIED, value)
  }
