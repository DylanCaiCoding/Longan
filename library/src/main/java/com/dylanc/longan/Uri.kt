@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dylanc.longan

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Size
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.net.UriCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.InputStream
import java.io.OutputStream

lateinit var fileProviderAuthority: String

val EXTERNAL_IMAGES_URI: Uri
  get() = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

val EXTERNAL_VIDEO_URI: Uri
  get() = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

val EXTERNAL_AUDIO_URI: Uri
  get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

@get:RequiresApi(Build.VERSION_CODES.Q)
val EXTERNAL_DOWNLOADS_URI: Uri
  get() = MediaStore.Downloads.EXTERNAL_CONTENT_URI

inline fun File.toUri(authority: String = fileProviderAuthority): Uri =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    FileProvider.getUriForFile(application, authority, this)
  } else {
    Uri.fromFile(this)
  }

inline fun queryMediaImages(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
) =
  contentResolver.query(EXTERNAL_IMAGES_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun queryMediaVideos(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
) =
  contentResolver.query(EXTERNAL_VIDEO_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun queryMediaAudios(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
) =
  contentResolver.query(EXTERNAL_AUDIO_URI, projection, selection, selectionArgs, sortOrder, block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun queryMediaDownload(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
) =
  contentResolver.query(EXTERNAL_DOWNLOADS_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun insertMediaImageUri(crossinline block: ContentValues.() -> Unit = {}) =
  contentResolver.insert(EXTERNAL_IMAGES_URI, block)

inline fun insertMediaVideoUri(crossinline block: ContentValues.() -> Unit = {}) =
  contentResolver.insert(EXTERNAL_VIDEO_URI, block)

inline fun insertMediaAudioUri(crossinline block: ContentValues.() -> Unit = {}) =
  contentResolver.insert(EXTERNAL_AUDIO_URI, block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun insertMediaDownloadUri(crossinline block: ContentValues.() -> Unit = {}) =
  contentResolver.insert(EXTERNAL_DOWNLOADS_URI, block)

inline fun Uri.update(
  id: Long = ContentUris.parseId(this),
  crossinline block: ContentValues.() -> Unit
): Int =
  contentResolver.update(this, "${BaseColumns._ID} = ?", arrayOf(id.toString()), block)

inline fun Uri.delete(id: Long = ContentUris.parseId(this)): Int =
  contentResolver.delete(this, "${BaseColumns._ID} = ?", arrayOf(id.toString()))

inline fun Uri.openInputStream(block: (InputStream) -> Unit) =
  contentResolver.openInputStream(this)?.use(block)

inline fun Uri.openOutputStream(block: (OutputStream) -> Unit) =
  contentResolver.openOutputStream(this)?.use(block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun Uri.loadThumbnail(width: Int, height: Int, signal: CancellationSignal? = null) =
  loadThumbnail(Size(width, height), signal)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun Uri.loadThumbnail(size: Size, signal: CancellationSignal? = null) =
  contentResolver.loadThumbnail(this, size, signal)

inline fun Uri.observeContentChange(
  owner: LifecycleOwner,
  crossinline block: (Boolean) -> Unit
) {
  val observer = object : ContentObserver(mainHandler.value) {
    override fun onChange(selfChange: Boolean) = block(selfChange)
  }
  owner.doOnLifecycle(
    onCreate = { contentResolver.registerContentObserver(this, true, observer) },
    onDestroy = { contentResolver.unregisterContentObserver(observer) }
  )
}

inline val Uri.fileExtension: String?
  get() = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

inline val Uri.mimeType: String?
  get() = contentResolver.getType(this)
