@file:Suppress("unused")

package com.dylanc.grape

import android.os.Environment

/**
 * @author Dylan Cai
 */

val cacheDirPath
  get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable())
    externalCacheDirPath
  else
    internalCacheDirPath

val internalCacheDirPath: String get() = application.cacheDir.absolutePath

val internalFileDirPath: String get() = application.filesDir.absolutePath

val internalMusicDirPath get() = application.getFileStreamPath(Environment.DIRECTORY_MUSIC)?.absolutePath

val externalCacheDirPath get() = application.externalCacheDir?.absolutePath

val externalFilesDirPath get() = application.getExternalFilesDir(null)

val externalMusicDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath

val externalPodcastsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_PODCASTS)?.absolutePath

val externalRingtonesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_RINGTONES)?.absolutePath

val externalAlarmsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_ALARMS)?.absolutePath

val externalNotificationsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS)?.absolutePath

val externalPicturesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath

val externalMoviesDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.absolutePath

val externalDownloadsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath

val externalDocumentsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath

val externalScreenshotsDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS)?.absolutePath

val externalAudiobooksDirPath get() = application.getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS)?.absolutePath