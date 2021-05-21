@file:Suppress("unused")

package com.dylanc.longan

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import kotlin.DeprecationLevel.ERROR


inline fun downloadRequest(url: String, block: DownloadRequestBuilder.() -> Unit) =
  DownloadRequest(url).apply(block)

interface DownloadRequestBuilder {
  val url: String

  var title: CharSequence
    @Deprecated(NO_GETTER, level = ERROR) get

  var description: CharSequence
    @Deprecated(NO_GETTER, level = ERROR) get

  var mimeType: String
    @Deprecated(NO_GETTER, level = ERROR) get

  var allowedNetworkTypes: Int
    @Deprecated(NO_GETTER, level = ERROR) get

  var notificationVisibility: Int
    @Deprecated(NO_GETTER, level = ERROR) get

  var allowedOverRoaming: Boolean
    @Deprecated(NO_GETTER, level = ERROR) get

  var destinationUri: Uri
    @Deprecated(NO_GETTER, level = ERROR) get

  fun addHeader(header: String, value: String)

  fun destinationInExternalFilesDir(dirType: String, subPath: String)
  fun destinationInExternalPublicDir(dirType: String, subPath: String)

  fun download(onComplete: (Uri) -> Unit): DownloadCompleteReceiver
}

class DownloadRequest(override val url: String) : DownloadRequestBuilder {
  private val request = DownloadManager.Request(Uri.parse(url))

  override var title: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setTitle(value)
    }

  override var description: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setDescription(value)
    }

  override var mimeType: String
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setMimeType(value)
    }

  override var allowedNetworkTypes: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setAllowedNetworkTypes(value)
    }

  override var notificationVisibility: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setNotificationVisibility(value)
    }

  override var allowedOverRoaming: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setAllowedOverRoaming(value)
    }
  override var destinationUri: Uri
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setDestinationUri(value)
    }

  override fun addHeader(header: String, value: String) {
    request.addRequestHeader(header, value)
  }

  override fun destinationInExternalFilesDir(dirType: String, subPath: String) {
    request.setDestinationInExternalFilesDir(application, dirType, subPath)
  }

  override fun destinationInExternalPublicDir(dirType: String, subPath: String) {
    request.setDestinationInExternalPublicDir(dirType, subPath)
  }

  override fun download(onComplete: (Uri) -> Unit): DownloadCompleteReceiver {
    val downloadManager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadId = downloadManager.enqueue(request)
    //TODO 支持监听下载进度，暂停下载
    return DownloadCompleteReceiver(downloadId, onComplete).also {
      application.registerReceiver(it, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
  }
}

@Suppress("MemberVisibilityCanBePrivate")
class DownloadCompleteReceiver(val downloadId: Long, private val onComplete: (Uri) -> Unit) : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
    if (id == downloadId) {
      val downloadManager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
      downloadManager.query(DownloadManager.Query().setFilterById(downloadId))?.use { cursor ->
        if (cursor.moveToFirst()) {
          val uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
          val uri = if (uriString.startsWith("file")) {
            File(Uri.parse(uriString).path!!).toUri()
          } else {
            Uri.parse(uriString)
          }
          onComplete(uri)
        }
      }
    }
  }
}
