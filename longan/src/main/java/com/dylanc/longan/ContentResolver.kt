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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
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
): R? =
  query(uri, projection, selection, selectionArgs, sortOrder)?.use(block)

inline fun <R> ContentResolver.queryFirst(
  uri: Uri,
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(uri, projection, selection, selectionArgs, sortOrder) {
    if (it.moveToFirst()) block(it) else null
  }

inline fun <R> ContentResolver.queryLast(
  uri: Uri,
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(uri, projection, selection, selectionArgs, sortOrder) {
    if (it.moveToLast()) block(it) else null
  }

inline fun <R> ContentResolver.queryMediaImages(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(EXTERNAL_MEDIA_IMAGES_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun <R> ContentResolver.queryMediaVideos(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(EXTERNAL_MEDIA_VIDEO_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun <R> ContentResolver.queryMediaAudios(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(EXTERNAL_MEDIA_AUDIO_URI, projection, selection, selectionArgs, sortOrder, block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun <R> ContentResolver.queryMediaDownloads(
  projection: Array<String>? = null,
  selection: String? = null,
  selectionArgs: Array<String>? = null,
  sortOrder: String? = null,
  block: (Cursor) -> R
): R? =
  query(EXTERNAL_MEDIA_DOWNLOADS_URI, projection, selection, selectionArgs, sortOrder, block)

inline fun ContentResolver.insert(uri: Uri, crossinline block: ContentValues.() -> Unit = {}): Uri? =
  contentResolver.insert(uri, contentValues(block))

inline fun ContentResolver.insertMediaImage(crossinline block: ContentValues.() -> Unit = {}): Uri? =
  contentResolver.insert(EXTERNAL_MEDIA_IMAGES_URI, block)

inline fun ContentResolver.insertMediaVideo(crossinline block: ContentValues.() -> Unit = {}): Uri? =
  contentResolver.insert(EXTERNAL_MEDIA_VIDEO_URI, block)

inline fun ContentResolver.insertMediaAudio(crossinline block: ContentValues.() -> Unit = {}): Uri? =
  contentResolver.insert(EXTERNAL_MEDIA_AUDIO_URI, block)

@RequiresApi(Build.VERSION_CODES.Q)
inline fun ContentResolver.insertMediaDownload(crossinline block: ContentValues.() -> Unit = {}): Uri? =
  contentResolver.insert(EXTERNAL_MEDIA_DOWNLOADS_URI, block)

inline fun ContentResolver.update(
  @RequiresPermission.Write uri: Uri,
  where: String? = null,
  selectionArgs: Array<String>? = null,
  crossinline block: ContentValues.() -> Unit
): Int =
  update(uri, contentValues(block), where, selectionArgs)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun ContentResolver.delete(
  @RequiresPermission.Write uri: Uri,
  where: String? = null,
  selectionArgs: Array<String>? = null
): Int =
  delete(uri, where, selectionArgs)
