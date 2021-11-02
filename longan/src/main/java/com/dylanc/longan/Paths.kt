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

import android.os.Environment


inline val cacheDirPath: String
  get() = if (isExternalStorageWritable || !isExternalStorageRemovable)
    externalCacheDirPath.orEmpty()
  else
    internalCacheDirPath

inline val externalCacheDirPath: String?
  get() = application.externalCacheDir?.absolutePath

inline val externalFilesDirPath: String?
  get() = application.getExternalFilesDir(null)?.absolutePath

inline val externalPicturesDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath

inline val externalMoviesDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath

inline val externalDownloadsDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath

inline val externalDocumentsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_DOCUMENTS)?.absolutePath

inline val externalMusicDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath

inline val externalPodcastsDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath

inline val externalRingtonesDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.absolutePath

inline val externalAlarmsDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath

inline val externalNotificationsDirPath: String?
  get() = application.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath

inline val internalCacheDirPath: String
  get() = application.cacheDir.absolutePath

inline val internalFileDirPath: String
  get() = application.filesDir.absolutePath

inline val internalPicturesDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_PICTURES)?.absolutePath

inline val internalMoviesDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_MOVIES)?.absolutePath

inline val internalDownloadsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_DOWNLOADS)?.absolutePath

inline val internalDocumentsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_DOCUMENTS)?.absolutePath

inline val internalMusicDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_MUSIC)?.absolutePath

inline val internalPodcastsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_PODCASTS)?.absolutePath

inline val internalRingtonesDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_RINGTONES)?.absolutePath

inline val internalAlarmsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_ALARMS)?.absolutePath

inline val internalNotificationsDirPath: String?
  get() = application.getFileStreamPath(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath

/**
 * Checks if a volume containing external storage is available for read and write.
 */
inline val isExternalStorageWritable: Boolean
  get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * Checks if a volume containing external storage is available to at least read.
 */
inline val isExternalStorageReadable: Boolean
  get() = Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)

inline val isExternalStorageRemovable: Boolean
  get() = Environment.isExternalStorageRemovable()
