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

import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Size
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.OutputStream


lateinit var fileProviderAuthority: String

inline val EXTERNAL_MEDIA_IMAGES_URI: Uri
  get() = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

inline val EXTERNAL_MEDIA_VIDEO_URI: Uri
  get() = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

inline val EXTERNAL_MEDIA_AUDIO_URI: Uri
  get() = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

@get:RequiresApi(Build.VERSION_CODES.Q)
inline val EXTERNAL_MEDIA_DOWNLOADS_URI: Uri
  get() = MediaStore.Downloads.EXTERNAL_CONTENT_URI

fun File.toUri(authority: String = fileProviderAuthority): Uri =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    FileProvider.getUriForFile(application, authority, this)
  } else {
    Uri.fromFile(this)
  }

inline fun Uri.update(crossinline block: ContentValues.() -> Unit): Boolean =
  contentResolver.update(
    this, "${BaseColumns._ID} = ?",
    arrayOf(ContentUris.parseId(this).toString()), block
  ) > 0

@ExperimentalApi
fun Uri.delete(launcher: ActivityResultLauncher<IntentSenderRequest>): Boolean =
  @Suppress("DEPRECATION")
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
    val projection = arrayOf(MediaStore.MediaColumns.DATA)
    contentResolver.queryFirst(this, projection) { cursor ->
      File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))).delete()
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

inline fun <R> Uri.openFileDescriptor(mode: String = "r", crossinline block: (ParcelFileDescriptor) -> R): R? =
  contentResolver.openFileDescriptor(this, mode)?.use(block)

inline fun <R> Uri.openInputStream(crossinline block: (InputStream) -> R): R? =
  contentResolver.openInputStream(this)?.use(block)

inline fun <R> Uri.openOutputStream(crossinline block: (OutputStream) -> R): R? =
  contentResolver.openOutputStream(this)?.use(block)

@RequiresApi(Build.VERSION_CODES.Q)
fun Uri.loadThumbnail(width: Int, height: Int, signal: CancellationSignal? = null): Bitmap =
  contentResolver.loadThumbnail(this, Size(width, height), signal)

inline val Uri.fileExtension: String?
  get() = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

inline val Uri.mimeType: String?
  get() = contentResolver.getType(this)

val Uri.size: Long
  get() = try {
    contentResolver.openFileDescriptor(this, "r")?.statSize
  } catch (e: FileNotFoundException) {
    contentResolver.queryFirst(this, arrayOf(OpenableColumns.SIZE)) { cursor ->
      cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE))
    }
  } ?: 0
