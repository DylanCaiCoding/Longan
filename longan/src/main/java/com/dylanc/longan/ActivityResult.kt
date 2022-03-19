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

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS
import androidx.annotation.CallSuper
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
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

fun ActivityResultCaller.startActivityLauncher(callback: ActivityResultCallback<ActivityResult>) =
  registerForActivityResult(StartActivityForResult(), callback)

fun ActivityResultCaller.startIntentSenderLauncher(callback: ActivityResultCallback<ActivityResult>) =
  registerForActivityResult(StartIntentSenderForResult(), callback)

fun ActivityResultCaller.requestPermissionLauncher(
  onGranted: () -> Unit,
  onDenied: AppSettingsScope.() -> Unit,
  onShowRequestRationale: PermissionScope.() -> Unit
): ActivityResultLauncher<String> {
  var permissionLauncher: ActivityResultLauncher<String>? = null
  var permission: String? = null
  val launchAppSettingsLauncher = appSettingsLauncher {
    permissionLauncher?.launch(permission)
  }
  permissionLauncher = registerForActivityResult(RequestPermissionContract()) {
    permission = it.first
    when {
      it.second -> onGranted()
      !permission.isNullOrEmpty() && ActivityCompat.shouldShowRequestPermissionRationale(topActivity, permission!!) ->
        onShowRequestRationale(PermissionScope { permissionLauncher?.launch(permission) })
      else -> onDenied(AppSettingsScope { launchAppSettingsLauncher.launch() })
    }
  }
  return permissionLauncher
}

fun ActivityResultCaller.requestPermissionLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(RequestPermission(), callback)

fun ActivityResultCaller.requestMultiplePermissionsLauncher(
  onAllGranted: () -> Unit,
  onDenied: AppSettingsScope.(List<String>) -> Unit,
  onShowRequestRationale: PermissionsScope.(List<String>) -> Unit
): ActivityResultLauncher<Array<String>> {
  var permissionsLauncher: ActivityResultLauncher<Array<String>>? = null
  val deniedList = mutableListOf<String>()
  val launchAppSettingsLauncher = appSettingsLauncher {
    permissionsLauncher?.launch(deniedList.toTypedArray())
  }
  permissionsLauncher = requestMultiplePermissionsLauncher { result ->
    if (result.containsValue(false)) {
      deniedList.clear()
      deniedList.addAll(result.asIterable().filter { !it.value }.map { it.key })
      val explainableList = deniedList.filter {
        ActivityCompat.shouldShowRequestPermissionRationale(topActivity, it)
      }
      if (explainableList.isNotEmpty()) {
        onShowRequestRationale(PermissionsScope {
          permissionsLauncher?.launch(explainableList.toTypedArray())
        }, explainableList)
      } else {
        onDenied(AppSettingsScope { launchAppSettingsLauncher.launch() }, deniedList)
      }
    } else {
      onAllGranted()
    }
  }
  return permissionsLauncher
}

fun ActivityResultCaller.requestMultiplePermissionsLauncher(callback: ActivityResultCallback<Map<String, Boolean>>) =
  registerForActivityResult(RequestMultiplePermissions(), callback)

fun ActivityResultCaller.takePicturePreviewLauncher(callback: ActivityResultCallback<Bitmap>) =
  registerForActivityResult(TakePicturePreview(), callback)

fun ActivityResultCaller.takePictureLauncher(callback: ActivityResultCallback<Boolean>) =
  SaveToUriLauncher(registerForActivityResult(TakePicture(), callback), EXTERNAL_MEDIA_IMAGES_URI, "jpg")

fun ActivityResultCaller.takeVideoLauncher(callback: ActivityResultCallback<Bitmap>) =
  SaveToUriLauncher(registerForActivityResult(TakeVideo(), callback), EXTERNAL_MEDIA_VIDEO_URI, "mp4")

fun ActivityResultCaller.pickContactLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(PickContact(), callback)

fun ActivityResultCaller.pickContentLauncher(callback: ActivityResultCallback<Uri>) =
  MediaUriResultLauncher(registerForActivityResult(PickContentContract(), callback))

fun ActivityResultCaller.getContentLauncher(callback: ActivityResultCallback<Uri>) =
  MediaUriResultLauncher(registerForActivityResult(GetContent(), callback))

fun ActivityResultCaller.getMultipleContentsLauncher(callback: ActivityResultCallback<List<Uri>>) =
  MediaUriResultLauncher(registerForActivityResult(GetMultipleContents(), callback))

fun ActivityResultCaller.openDocumentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(OpenDocument(), callback)

fun ActivityResultCaller.openMultipleDocumentsLauncher(callback: ActivityResultCallback<List<Uri>>) =
  registerForActivityResult(OpenMultipleDocuments(), callback)

fun ActivityResultCaller.openDocumentTreeLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(OpenDocumentTree(), callback)

fun ActivityResultCaller.createDocumentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(CreateDocument(), callback)

fun ActivityResultCaller.appSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchAppSettingsContract(), callback)

fun ActivityResultCaller.notificationSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchNotificationSettingsContract(), callback)

fun ActivityResultCaller.cropPictureLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(CropPictureContract(), callback)

fun ActivityResultCaller.enableLocationLauncher(
  onLocationEnabled: LocationScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  var enableLocationLauncher: ActivityResultLauncher<Unit>? = null
  enableLocationLauncher = registerForActivityResult(EnableLocationContract()) {
    onLocationEnabled(LocationScope { enableLocationLauncher?.launch() }, it)
  }
  return enableLocationLauncher
}

@RequiresPermission(Manifest.permission.BLUETOOTH)
fun ActivityResultCaller.enableBluetoothLauncher(
  onLocationDisabled: LocationScope.() -> Unit,
  onBluetoothEnabled: BluetoothScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  val enableBluetoothLauncher = enableBluetoothLauncher(onBluetoothEnabled)
  val enableLocationLauncher = enableLocationLauncher { enabled ->
    if (enabled) {
      enableBluetoothLauncher.launch()
    } else {
      onLocationDisabled(this)
    }
  }
  return enableLocationLauncher
}

@RequiresPermission(Manifest.permission.BLUETOOTH)
fun ActivityResultCaller.enableBluetoothLauncher(
  onBluetoothEnabled: BluetoothScope.(Boolean) -> Unit
): ActivityResultLauncher<Unit> {
  var enableBluetoothLauncher: ActivityResultLauncher<Unit>? = null
  enableBluetoothLauncher = registerForActivityResult(EnableBluetoothContract()) {
    onBluetoothEnabled(BluetoothScope { enableBluetoothLauncher?.launch() }, it)
  }
  return enableBluetoothLauncher
}

fun ActivityResultCaller.launchWifiSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchWifiSettingsContract(), callback)

@RequiresApi(Build.VERSION_CODES.Q)
fun ActivityResultCaller.openWifiPanelLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(OpenWifiPanelContract(), callback)

fun ActivityResultLauncher<CropPictureRequest>.launch(
  inputUri: Uri,
  vararg extras: Pair<String, Any?>,
  outputUri: Uri? = null
) =
  launch(CropPictureRequest(inputUri, outputUri, bundleOf(*extras)))

class CropPictureRequest constructor(
  val inputUri: Uri,
  val outputUri: Uri? = null,
  val extras: Bundle = Bundle()
)

open class CropPictureContract : ActivityResultContract<CropPictureRequest, Boolean>() {

  @CallSuper
  override fun createIntent(context: Context, input: CropPictureRequest) =
    Intent("com.android.camera.action.CROP")
      .setDataAndType(input.inputUri, "image/*")
      .putExtra(MediaStore.EXTRA_OUTPUT, input.outputUri ?: contentResolver.insertMediaImage())
      .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
      .putExtra("return-data", false)
      .putExtras(input.extras)
      .grantReadUriPermission()

  override fun parseResult(resultCode: Int, intent: Intent?) = resultCode == Activity.RESULT_OK
}

class PickContentContract : ActivityResultContract<String, Uri>() {

  override fun createIntent(context: Context, input: String?) =
    Intent(ACTION_PICK).setType(input)

  override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
    return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data!!
  }
}

class LaunchAppSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
      .setData(Uri.fromParts("package", context.packageName, null))

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class LaunchNotificationSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    } else {
      Intent("android.settings.APP_NOTIFICATION_SETTINGS")
        .putExtra("app_package", context.packageName)
        .putExtra("app_uid", context.applicationInfo.uid)
    }

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class EnableLocationContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) = isLocationEnabled

  override fun getSynchronousResult(context: Context, input: Unit?): SynchronousResult<Boolean>? =
    if (isLocationEnabled) SynchronousResult(true) else null
}

class EnableBluetoothContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    resultCode == Activity.RESULT_OK

  override fun getSynchronousResult(context: Context, input: Unit?): SynchronousResult<Boolean>? =
    if (isBluetoothEnabled) SynchronousResult(true) else null
}

@RequiresApi(Build.VERSION_CODES.Q)
class OpenWifiPanelContract : ActivityResultContract<Unit, Unit>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class LaunchWifiSettingsContract : ActivityResultContract<Unit, Unit>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_WIFI_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) = Unit
}

class RequestPermissionContract : ActivityResultContract<String, Pair<String, Boolean>>() {
  private lateinit var permission: String

  override fun createIntent(context: Context, input: String): Intent {
    permission = input
    return Intent(ACTION_REQUEST_PERMISSIONS).putExtra(EXTRA_PERMISSIONS, arrayOf(input))
  }

  override fun parseResult(resultCode: Int, intent: Intent?): Pair<String, Boolean> {
    if (intent == null || resultCode != Activity.RESULT_OK) return permission to false
    val grantResults = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS")
    return permission to if (grantResults == null || grantResults.isEmpty()) false else grantResults[0] == PackageManager.PERMISSION_GRANTED
  }

  override fun getSynchronousResult(context: Context, input: String?): SynchronousResult<Pair<String, Boolean>>? =
    when {
      input == null -> SynchronousResult("" to false)
      ContextCompat.checkSelfPermission(context, input) == PackageManager.PERMISSION_GRANTED -> {
        SynchronousResult(input to true)
      }
      else -> null
    }
}

class MediaUriResultLauncher(launcher: ActivityResultLauncher<String>) : DecorActivityResultLauncher<String>(launcher) {

  fun launchForImage() = launch("image/*")

  fun launchForVideo() = launch("video/*")
}

class SaveToUriLauncher(launcher: ActivityResultLauncher<Uri>, private val uri: Uri, private val suffixes: String) :
  DecorActivityResultLauncher<Uri>(launcher) {

  fun launchAndSaveToCache() =
    File("$externalCacheDirPath$fileSeparator${System.currentTimeMillis()}.$suffixes").toUri().also { launch(it) }
}

abstract class DecorActivityResultLauncher<T>(private val launcher: ActivityResultLauncher<T>) : ActivityResultLauncher<T>() {

  override fun launch(input: T?, options: ActivityOptionsCompat?) = launcher.launch(input, options)

  override fun unregister() = launcher.unregister()

  override fun getContract(): ActivityResultContract<T, *> = launcher.contract
}

fun interface AppSettingsScope {
  fun launchAppSettings()
}

fun interface PermissionScope {
  fun requestPermissionAgain()
}

fun interface PermissionsScope {
  fun requestDeniedPermissions()
}

fun interface LocationScope {
  fun enableLocation()
}

fun interface BluetoothScope {
  fun enableBluetooth()
}

@SuppressLint("InlinedApi")
@IntDef(
  FLAG_GRANT_READ_URI_PERMISSION, FLAG_GRANT_WRITE_URI_PERMISSION,
  FLAG_FROM_BACKGROUND, FLAG_DEBUG_LOG_RESOLUTION, FLAG_EXCLUDE_STOPPED_PACKAGES,
  FLAG_INCLUDE_STOPPED_PACKAGES, FLAG_GRANT_PERSISTABLE_URI_PERMISSION,
  FLAG_GRANT_PREFIX_URI_PERMISSION, FLAG_ACTIVITY_MATCH_EXTERNAL,
  FLAG_ACTIVITY_NO_HISTORY, FLAG_ACTIVITY_SINGLE_TOP, FLAG_ACTIVITY_NEW_TASK,
  FLAG_ACTIVITY_MULTIPLE_TASK, FLAG_ACTIVITY_CLEAR_TOP,
  FLAG_ACTIVITY_FORWARD_RESULT, FLAG_ACTIVITY_PREVIOUS_IS_TOP,
  FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS, FLAG_ACTIVITY_BROUGHT_TO_FRONT,
  FLAG_ACTIVITY_RESET_TASK_IF_NEEDED, FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY,
  FLAG_ACTIVITY_NEW_DOCUMENT, FLAG_ACTIVITY_NO_USER_ACTION,
  FLAG_ACTIVITY_REORDER_TO_FRONT, FLAG_ACTIVITY_NO_ANIMATION,
  FLAG_ACTIVITY_CLEAR_TASK, FLAG_ACTIVITY_TASK_ON_HOME,
  FLAG_ACTIVITY_RETAIN_IN_RECENTS, FLAG_ACTIVITY_LAUNCH_ADJACENT
)
@Retention(AnnotationRetention.SOURCE)
private annotation class Flag
