@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.dylanc.longan

import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.provider.BaseColumns
import android.provider.MediaStore
import android.util.Size
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.InputStream
import java.io.OutputStream

lateinit var fileProviderAuthority: String

val EXTERNAL_MEDIA_IMAGES_URI: Uri
  get() = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

val EXTERNAL_MEDIA_VIDEO_URI: Uri
  get() = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

val EXTERNAL_MEDIA_AUDIO_URI: Uri
  get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

@get:RequiresApi(Build.VERSION_CODES.Q)
val EXTERNAL_MEDIA_DOWNLOADS_URI: Uri
  get() = MediaStore.Downloads.EXTERNAL_CONTENT_URI

inline fun File.toUri(authority: String = fileProviderAuthority): Uri =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    FileProvider.getUriForFile(application, authority, this)
  } else {
    Uri.fromFile(this)
  }

inline fun Uri.update(crossinline block: ContentValues.() -> Unit) =
  contentResolver.update(
    this, "${BaseColumns._ID} = ?",
    arrayOf(ContentUris.parseId(this).toString()), block
  )

@ExperimentalApi
fun Uri.delete(launcher: ActivityResultLauncher<IntentSenderRequest>): Boolean =
  @Suppress("DEPRECATION")
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    val projection = arrayOf(MediaStore.MediaColumns.DATA)
    contentResolver.queryFirst(this, projection) { cursor ->
      val file = File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)))
      file.delete()
    } ?: false
  } else {
    try {
      val id: Long = ContentUris.parseId(this)
      contentResolver.delete(this, "${BaseColumns._ID} = ?", arrayOf(id.toString())) > 0
    } catch (securityException: SecurityException) {
      val recoverableSecurityException = securityException as? RecoverableSecurityException
        ?: throw RuntimeException(securityException.message, securityException)
      val intentSender =
        recoverableSecurityException.userAction.actionIntent.intentSender
      launcher.launch(IntentSenderRequest.Builder(intentSender).build())
      false
    }
  }

inline fun Uri.openFileDescriptor(mode: String = "r", block: (ParcelFileDescriptor) -> Unit) =
  contentResolver.openFileDescriptor(this, mode)?.use(block)

inline fun Uri.openInputStream(block: (InputStream) -> Unit) =
  contentResolver.openInputStream(this)?.use(block)

inline fun Uri.openOutputStream(block: (OutputStream) -> Unit) =
  contentResolver.openOutputStream(this)?.use(block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun Uri.loadThumbnail(width: Int, height: Int, signal: CancellationSignal? = null) =
  contentResolver.loadThumbnail(this, Size(width, height), signal)

inline fun Uri.observeContentChanged(
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
