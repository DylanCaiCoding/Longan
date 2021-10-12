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

import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore

/**
 * @author Dylan Cai
 */

inline fun contentValues(block: ContentValues.() -> Unit) = ContentValues().apply(block)

inline var ContentValues.displayName: String?
  get() = get(MediaStore.MediaColumns.DISPLAY_NAME) as String?
  set(value) = put(MediaStore.MediaColumns.DISPLAY_NAME, value)

inline var ContentValues.relativePath: String?
  get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    get(MediaStore.MediaColumns.RELATIVE_PATH) as String?
  } else null
  set(value) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      put(MediaStore.MediaColumns.RELATIVE_PATH, value)
    }
  }

inline var ContentValues.mimeType: String?
  get() = get(MediaStore.MediaColumns.MIME_TYPE) as String?
  set(value) = put(MediaStore.MediaColumns.MIME_TYPE, value)

inline var ContentValues.imageDescription: String?
  get() = get(MediaStore.Images.Media.DESCRIPTION) as String?
  set(value) = put(MediaStore.Images.Media.DESCRIPTION, value)

inline var ContentValues.videoDescription: String?
  get() = get(MediaStore.Video.Media.DESCRIPTION) as String?
  set(value) = put(MediaStore.Video.Media.DESCRIPTION, value)

inline var ContentValues.dateAdded: Long?
  get() = get(MediaStore.MediaColumns.DATE_ADDED) as Long?
  set(value) = put(MediaStore.MediaColumns.DATE_ADDED, value)

inline var ContentValues.dateModified: Long?
  get() = get(MediaStore.MediaColumns.DATE_MODIFIED) as Long?
  set(value) = put(MediaStore.MediaColumns.DATE_MODIFIED, value)
