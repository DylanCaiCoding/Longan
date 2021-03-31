@file:Suppress("unused")

package com.dylanc.grape

import android.os.Environment

/**
 * @author Dylan Cai
 */

inline val cacheDirPath
  get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable())
    externalCacheDirPath
  else
    internalCacheDirPath

inline val internalCacheDirPath: String get() = application.cacheDir.absolutePath

inline val internalFileDirPath: String get() = application.filesDir.absolutePath

inline val internalMusicDirPath get() = application.getFileStreamPath(Environment.DIRECTORY_MUSIC)?.absolutePath

inline val externalCacheDirPath get() = application.externalCacheDir?.absolutePath

inline val externalFilesDirPath get() = application.getExternalFilesDir(null)

inline val externalMusicDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath

inline val externalPodcastsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath

inline val externalRingtonesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.absolutePath

inline val externalAlarmsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath

inline val externalNotificationsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath

inline val externalPicturesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath

inline val externalMoviesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath

inline val externalDownloadsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath

inline val externalDocumentsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath