@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dylanc.grape

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Size
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.core.net.UriCompat
import java.io.File
import java.io.InputStream
import java.io.OutputStream


fun Uri.toFile(): File =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    when (this.scheme) {
      ContentResolver.SCHEME_FILE -> {
        File(requireNotNull(path) { "Uri path is null: $this" })
      }
      ContentResolver.SCHEME_CONTENT -> {
        val fileName = fileName ?: "${System.currentTimeMillis()}.$fileExtension"
        File("${cacheDirPath}/$fileName").apply {
          openInputStream()?.use { inputStream ->
            outputStream().use { inputStream.copyTo(it) }
          }
        }
      }
      else -> {
        throw IllegalArgumentException("Uri lacks 'file' scheme or 'content' scheme.")
      }
    }
  } else {
    contentResolver.query(this) { cursor ->
      @Suppress("DEPRECATION")
      val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
      if (cursor.moveToFirst()) {
        cursor.getString(columnIndex)
      } else {
        null
      }
    }.let {
      File(requireNotNull(it) { "Uri path is null: $this" })
    }
  }

inline fun File.toUri(authority: String = "$packageName.provider"): Uri =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    FileProvider.getUriForFile(application, authority, this)
  } else {
    Uri.fromFile(this)
  }

inline fun insertMediaImageUri(block: ContentValues.() -> Unit = {}) =
  insertMediaUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, block)

inline fun insertMediaVideoUri(block: ContentValues.() -> Unit = {}) =
  insertMediaUri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, block)

inline fun insertMediaAudioUri(block: ContentValues.() -> Unit = {}) =
  insertMediaUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, block)

//inline fun insertMediaDownloadUri(block: ContentValues.() -> Unit = {}) =
//  insertMediaUri(MediaStore.Downloads.EXTERNAL_CONTENT_URI, block)

inline fun insertMediaUri(uri: Uri, block: ContentValues.() -> Unit = {}) =
  ContentValues().apply(block).let { contentResolver.insert(uri, it) }

inline fun Uri.update(id: Long = ContentUris.parseId(this), block: ContentValues.() -> Unit): Int {
  val selection = "${BaseColumns._ID} = ?"
  val selectionArgs = arrayOf(id.toString())
  return contentResolver.update(this, selection, selectionArgs, block)
}

inline fun Uri.delete(id: Long = ContentUris.parseId(this)): Int {
  val selection = "${BaseColumns._ID} = ?"
  val selectionArgs = arrayOf(id.toString())
  return contentResolver.delete(this, selection, selectionArgs)
}

inline val Uri.fileExtension: String?
  get() = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

inline val Uri.fileName: String?
  get() = contentResolver.query(this, arrayOf(OpenableColumns.DISPLAY_NAME)) { cursor ->
    if (cursor.moveToFirst())
      cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    else
      null
  }

inline val Uri.mimeType: String?
  get() = contentResolver.getType(this)

inline val Uri.size: Long
  get() = contentResolver.query(this, arrayOf(OpenableColumns.SIZE)) { cursor ->
    if (cursor.moveToFirst())
      cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE))
    else
      0
  } ?: 0

inline fun Uri.openInputStream(): InputStream? = contentResolver.openInputStream(this)

inline fun Uri.openOutputStream(): OutputStream? = contentResolver.openOutputStream(this)

inline fun Uri.loadThumbnail(width: Int, height: Int, signal: CancellationSignal? = null) =
  loadThumbnail(Size(width, height), signal)

inline fun Uri.loadThumbnail(size: Size, signal: CancellationSignal? = null) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    contentResolver.loadThumbnail(this, size, signal)
  }
}

inline fun Uri.toSafeString() = UriCompat.toSafeString(this)