@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner


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

inline fun ContentResolver.insert(uri: Uri, crossinline block: ContentValues.() -> Unit = {}) =
  contentResolver.insert(uri, contentValues(block))

inline fun ContentResolver.update(
  @RequiresPermission.Write uri: Uri,
  where: String? = null,
  selectionArgs: Array<String>? = null,
  crossinline block: ContentValues.() -> Unit
) =
  update(uri, contentValues(block), where, selectionArgs)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun ContentResolver.delete(
  @RequiresPermission.Write uri: Uri,
  where: String? = null,
  selectionArgs: Array<String>? = null
) =
  delete(uri, where, selectionArgs)

inline fun contentValues(block: ContentValues.() -> Unit) = ContentValues().apply(block)

inline val Cursor.displayNameIndex: Int
  get() = getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)

inline val Cursor.sizeIndex: Int
  get() = getColumnIndexOrThrow(OpenableColumns.SIZE)

inline var ContentValues.displayName: String?
  get() = get(MediaStore.MediaColumns.DISPLAY_NAME) as String?
  set(value) = put(MediaStore.MediaColumns.DISPLAY_NAME, value)

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
  set(value) = put(MediaStore.MediaColumns.MIME_TYPE, value)

inline var ContentValues.dateAdded: Long?
  get() = get(MediaStore.MediaColumns.DATE_ADDED) as Long?
  set(value) = put(MediaStore.MediaColumns.DATE_ADDED, value)

inline var ContentValues.dateModified: Long?
  get() = get(MediaStore.MediaColumns.DATE_MODIFIED) as Long?
  set(value) = put(MediaStore.MediaColumns.DATE_MODIFIED, value)
