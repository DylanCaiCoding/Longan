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

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.DeprecationLevel.ERROR

@RequiresPermission("android.permission.ACCESS_DOWNLOAD_MANAGER")
fun download(url: String, block: DownloadRequestBuilder.() -> Unit) =
  DownloadRequestBuilder(url).apply(block).build()

inline fun <R> DownloadManager.query(downloadId: Long, block: (Cursor) -> R): R? =
  query(DownloadManager.Query().setFilterById(downloadId))?.use { cursor ->
    if (cursor.moveToFirst()) block(cursor) else null
  }

class DownloadRequestBuilder internal constructor(url: String) {
  private val request = DownloadManager.Request(Uri.parse(url))
  private val downloadManager = application.getSystemService<DownloadManager>()
  private var onComplete: ((Uri) -> Unit)? = null
  private var onChange: ((downloadedSize: Int, totalSize: Int, status: Int) -> Unit)? = null
  private var scheduleExecutor: ScheduledExecutorService? = null
  private var progressObserver: ContentObserver? = null
  private var downloadId: Long = -1

  var title: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setTitle(value)
    }

  var description: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setDescription(value)
    }

  var mimeType: String
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setMimeType(value)
    }

  var allowedNetworkTypes: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setAllowedNetworkTypes(value)
    }

  var allowedOverOverMetered: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setAllowedOverMetered(value)
    }

  var allowedOverRoaming: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setAllowedOverRoaming(value)
    }

  var notificationVisibility: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setNotificationVisibility(value)
    }

  var requiresCharging: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        request.setRequiresCharging(value)
      }
    }

  var requiresDeviceIdle: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        request.setRequiresDeviceIdle(value)
      }
    }

  var destinationUri: Uri
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      request.setDestinationUri(value)
    }

  fun addHeader(header: String, value: String) {
    request.addRequestHeader(header, value)
  }

  fun destinationInExternalFilesDir(dirType: String, subPath: String) {
    request.setDestinationInExternalFilesDir(application, dirType, subPath)
  }

  fun destinationInExternalPublicDir(dirType: String, subPath: String) {
    request.setDestinationInExternalPublicDir(dirType, subPath)
  }

  fun onComplete(block: (Uri) -> Unit) {
    onComplete = block
  }

  fun onChange(block: (downloadedSize: Int, totalSize: Int, status: Int) -> Unit) {
    onChange = block
  }

  internal fun build() {
    downloadId = downloadManager?.enqueue(request) ?: -1
    progressObserver = onChange?.let { DownloadProgressObserver() }?.also {
      contentResolver.registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, it)
    }
    application.registerReceiver(DownloadCompleteReceiver(), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
  }

  private inner class DownloadProgressObserver : ContentObserver(null) {
    init {
      scheduleExecutor = Executors.newSingleThreadScheduledExecutor()
    }

    override fun onChange(selfChange: Boolean) {
      scheduleExecutor?.scheduleAtFixedRate({
        mainThread {
          downloadManager?.query(downloadId) { cursor ->
            val downloadedSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val totalSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            onChange?.invoke(downloadedSize, totalSize, status)
          }
        }
      }, 0, 2, TimeUnit.SECONDS)
    }
  }

  private inner class DownloadCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
      if (id == downloadId) {
        if (scheduleExecutor?.isShutdown != true) {
          scheduleExecutor?.shutdown()
        }
        progressObserver?.let {
          contentResolver.unregisterContentObserver(it)
          progressObserver = null
        }
        onComplete?.let { onComplete ->
          downloadManager?.query(downloadId) { cursor ->
            val uriString = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
            onComplete(Uri.parse(uriString))
          }
        }
        application.unregisterReceiver(this)
      }
    }
  }
}
