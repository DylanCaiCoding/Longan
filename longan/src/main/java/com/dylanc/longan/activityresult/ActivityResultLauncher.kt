/*
 * Copyright (C) 2021. Dylan Cai
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

package com.dylanc.longan.activityresult

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.IntDef
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import com.dylanc.longan.*
import java.io.File

fun ActivityResultLauncher<Unit>.launch(options: ActivityOptionsCompat? = null) = launch(Unit, options)

@JvmName("launchVoid")
fun ActivityResultLauncher<Void>.launch(options: ActivityOptionsCompat? = null) = launch(null, options)

inline fun <reified T> ActivityResultLauncher<Array<T>>.launch(vararg input: T) = launch(arrayOf(*input))

inline fun <reified T : Activity> ActivityResultLauncher<Intent>.launch(vararg pairs: Pair<String, *>) =
  launch(topActivity.intentOf<T>(*pairs))

fun ActivityResultLauncher<IntentSenderRequest>.launch(
  intentSender: IntentSender,
  fillInIntent: Intent? = null,
  @Flag flagsValues: Int = 0,
  flagsMask: Int = 0
) =
  IntentSenderRequest.Builder(intentSender)
    .setFillInIntent(fillInIntent)
    .setFlags(flagsValues, flagsMask)
    .build()
    .let { launch(it) }

fun ActivityResultLauncher<CropPictureRequest>.launch(
  inputUri: Uri,
  vararg extras: Pair<String, Any?>,
  outputUri: Uri? = null
) =
  launch(CropPictureRequest(inputUri, outputUri, bundleOf(*extras)))

class SaveToUriLauncher(launcher: ActivityResultLauncher<Uri>, private val uri: Uri, private val suffixes: String) :
  DecorActivityResultLauncher<Uri>(launcher) {

  fun launchAndSaveToCache() =
    File("$externalCacheDirPath$fileSeparator${System.currentTimeMillis()}.$suffixes").toUri().also { launch(it) }
}

class MediaUriResultLauncher(launcher: ActivityResultLauncher<String>) : DecorActivityResultLauncher<String>(launcher) {

  fun launchForImage() = launch("image/*")

  fun launchForVideo() = launch("video/*")
}

abstract class DecorActivityResultLauncher<T>(private val launcher: ActivityResultLauncher<T>) : ActivityResultLauncher<T>() {

  override fun launch(input: T?, options: ActivityOptionsCompat?) = launcher.launch(input, options)

  override fun unregister() = launcher.unregister()

  override fun getContract(): ActivityResultContract<T, *> = launcher.contract
}

@SuppressLint("InlinedApi")
@IntDef(
  Intent.FLAG_GRANT_READ_URI_PERMISSION, Intent.FLAG_GRANT_WRITE_URI_PERMISSION,
  Intent.FLAG_FROM_BACKGROUND, Intent.FLAG_DEBUG_LOG_RESOLUTION, Intent.FLAG_EXCLUDE_STOPPED_PACKAGES,
  Intent.FLAG_INCLUDE_STOPPED_PACKAGES, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION,
  Intent.FLAG_GRANT_PREFIX_URI_PERMISSION, Intent.FLAG_ACTIVITY_MATCH_EXTERNAL,
  Intent.FLAG_ACTIVITY_NO_HISTORY, Intent.FLAG_ACTIVITY_SINGLE_TOP, Intent.FLAG_ACTIVITY_NEW_TASK,
  Intent.FLAG_ACTIVITY_MULTIPLE_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP,
  Intent.FLAG_ACTIVITY_FORWARD_RESULT, Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP,
  Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT,
  Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED, Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY,
  Intent.FLAG_ACTIVITY_NEW_DOCUMENT, Intent.FLAG_ACTIVITY_NO_USER_ACTION,
  Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, Intent.FLAG_ACTIVITY_NO_ANIMATION,
  Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_TASK_ON_HOME,
  Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS, Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT
)
@Retention(AnnotationRetention.SOURCE)
private annotation class Flag
