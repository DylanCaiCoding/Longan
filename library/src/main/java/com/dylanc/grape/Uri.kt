package com.dylanc.grape

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun Uri.toFile(): File =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    when (this.scheme) {
      ContentResolver.SCHEME_FILE -> {
        File(requireNotNull(path) { "Uri path is null: $this" })
      }
      ContentResolver.SCHEME_CONTENT -> {
        val fileName = fileName ?: "${System.currentTimeMillis()}.$fileExtension"
        val inputStream = requireNotNull(inputStream)
        File("${application.externalCacheDir!!.absolutePath}/$fileName")
          .apply {
            val fos = FileOutputStream(this)
            FileUtils.copy(inputStream, fos)
            fos.close()
            inputStream.close()
          }
      }
      else -> {
        throw IllegalArgumentException("Uri lacks 'file' scheme or 'content' scheme.")
      }
    }
  } else {
    val cursor = toCursor()
    val path: String? = cursor?.let {
      if (cursor.moveToFirst()) {
        @Suppress("DEPRECATION")
        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
      } else {
        null
      }
    }
    cursor?.close()
    File(requireNotNull(path) { "Uri path is null: $this" })
  }

val Uri.fileExtension: String?
  get() = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

val Uri.fileName: String?
  get() {
    val cursor = toCursor(arrayOf(OpenableColumns.DISPLAY_NAME))
    cursor?.let {
      if (it.moveToFirst())
        return it.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    cursor?.close()
    return null
  }

val Uri.mimeType: String?
  get() = application.contentResolver.getType(this)

val Uri.inputStream: InputStream?
  get() = application.contentResolver.openInputStream(this)

fun Uri.toCursor(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null
) =
  application.contentResolver.query(this, projection, selection, selectionArgs, sortOrder)
