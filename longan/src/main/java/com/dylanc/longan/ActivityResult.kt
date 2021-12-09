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

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat


fun ActivityResultLauncher<Unit>.launch() = launch(Unit)

inline fun <reified T : Activity> ActivityResultLauncher<Intent>.launch(vararg pairs: Pair<String, *>) =
  launch(topActivity.intentOf<T>(*pairs))

fun ActivityResultCaller.startActivityLauncher(callback: ActivityResultCallback<ActivityResult>) =
  registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)

fun ActivityResultCaller.startIntentSenderLauncher(callback: ActivityResultCallback<ActivityResult>) =
  registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult(), callback)

fun ActivityResultCaller.requestPermissionLauncher(
  permission: String,
  onGranted: () -> Unit,
  onDenied: AppSettingsScope.() -> Unit,
  onShowRequestRationale: PermissionScope.() -> Unit
): ActivityResultLauncher<String> {
  var permissionLauncher: ActivityResultLauncher<String>? = null
  val detailsSettingsLauncher = launchAppDetailsSettingsLauncher {
    permissionLauncher?.launch(permission)
  }
  permissionLauncher = requestPermissionLauncher {
    when {
      it != null -> onGranted()
      ActivityCompat.shouldShowRequestPermissionRationale(topActivity, permission) ->
        onShowRequestRationale(PermissionScope { permissionLauncher?.launch(permission) })
      else -> onDenied(AppSettingsScope { detailsSettingsLauncher.launch() })
    }
  }
  return permissionLauncher
}

fun ActivityResultCaller.requestPermissionLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(ActivityResultContracts.RequestPermission(), callback)

fun ActivityResultCaller.requestMultiplePermissionsLauncher(
  onAllGranted: () -> Unit,
  onDenied: AppSettingsScope.(List<String>) -> Unit,
  onShowRequestRationale: PermissionsScope.(List<String>) -> Unit
): ActivityResultLauncher<Array<String>> {
  var permissionsLauncher: ActivityResultLauncher<Array<String>>? = null
  var explainableList: List<String>? = null
  val detailsSettingsLauncher = launchAppDetailsSettingsLauncher {
    permissionsLauncher?.launch(explainableList!!.toTypedArray())
  }
  permissionsLauncher = requestMultiplePermissionsLauncher { result ->
    if (result.containsValue(false)) {
      val deniedList = result.filter { !it.value }.map { it.key }
      explainableList = deniedList.filter {
        ActivityCompat.shouldShowRequestPermissionRationale(topActivity, it)
      }
      if (explainableList?.isNotEmpty() == true) {
        onShowRequestRationale(PermissionsScope {
          permissionsLauncher?.launch(explainableList!!.toTypedArray())
        }, explainableList!!)
      } else {
        onDenied(AppSettingsScope { detailsSettingsLauncher.launch() }, deniedList)
      }
    } else {
      onAllGranted()
    }
  }
  return permissionsLauncher
}

fun ActivityResultCaller.requestMultiplePermissionsLauncher(callback: ActivityResultCallback<Map<String, Boolean>>) =
  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), callback)

fun ActivityResultCaller.takePicturePreviewLauncher(callback: ActivityResultCallback<Bitmap>) =
  registerForActivityResult(ActivityResultContracts.TakePicturePreview(), callback)

fun ActivityResultCaller.takePictureLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(ActivityResultContracts.TakePicture(), callback)

fun ActivityResultCaller.takeVideoLauncher(callback: ActivityResultCallback<Bitmap>) =
  registerForActivityResult(ActivityResultContracts.TakeVideo(), callback)

fun ActivityResultCaller.pickContactLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(ActivityResultContracts.PickContact(), callback)

fun ActivityResultCaller.pickContentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(PickContentContract(), callback)

fun ActivityResultCaller.getContentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(ActivityResultContracts.GetContent(), callback)

fun ActivityResultCaller.getMultipleContentsLauncher(callback: ActivityResultCallback<List<Uri>>) =
  registerForActivityResult(ActivityResultContracts.GetMultipleContents(), callback)

fun ActivityResultCaller.openDocumentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(ActivityResultContracts.OpenDocument(), callback)

fun ActivityResultCaller.openMultipleDocumentsLauncher(callback: ActivityResultCallback<List<Uri>>) =
  registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments(), callback)

fun ActivityResultCaller.openDocumentTreeLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(ActivityResultContracts.OpenDocumentTree(), callback)

fun ActivityResultCaller.createDocumentLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(ActivityResultContracts.CreateDocument(), callback)

fun ActivityResultCaller.launchAppDetailsSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchAppDetailsSettingsContract(), callback)

fun ActivityResultCaller.cropPictureLauncher(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(CropPictureContract(), callback)

fun ActivityResultCaller.enableLocationLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(EnableLocationContract(), callback)

fun ActivityResultCaller.enableBluetoothLauncher(callback: ActivityResultCallback<Boolean>) =
  registerForActivityResult(EnableBluetoothContract(), callback)

fun ActivityResultCaller.launchWifiSettingsLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(LaunchWifiSettingsContract(), callback)

@RequiresApi(Build.VERSION_CODES.Q)
fun ActivityResultCaller.openWifiPanelLauncher(callback: ActivityResultCallback<Unit>) =
  registerForActivityResult(OpenWifiPanelContract(), callback)

fun ActivityResultLauncher<CropPictureRequest>.launch(
  inputUri: Uri,
  aspectX: Int = 1,
  aspectY: Int = 1,
  outputX: Int = 512,
  outputY: Int = 512,
  outputContentValues: ContentValues = ContentValues(),
  onCreateIntent: ((Intent) -> Unit)? = null
) =
  launch(CropPictureRequest(inputUri, aspectX, aspectY, outputX, outputY, outputContentValues, onCreateIntent))

data class CropPictureRequest @JvmOverloads constructor(
  val inputUri: Uri,
  var aspectX: Int = 1,
  var aspectY: Int = 1,
  var outputX: Int = 512,
  var outputY: Int = 512,
  var outputContentValues: ContentValues = ContentValues(),
  var onCreateIntent: ((Intent) -> Unit)? = null
)

class CropPictureContract : ActivityResultContract<CropPictureRequest, Uri>() {
  private lateinit var outputUri: Uri

  @CallSuper
  override fun createIntent(context: Context, input: CropPictureRequest) =
    Intent("com.android.camera.action.CROP").apply {
      outputUri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        input.outputContentValues
      )!!
      setDataAndType(input.inputUri, "image/*")
      putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
      putExtra("aspectX", input.aspectX)
      putExtra("aspectY", input.aspectY)
      putExtra("outputX", input.outputX)
      putExtra("outputY", input.outputY)
      putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
      putExtra("return-data", false)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      }
      input.onCreateIntent?.invoke(this)
    }

  override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
    if (resultCode == Activity.RESULT_OK) outputUri else null
}

class PickContentContract : ActivityResultContract<String, Uri>() {

  override fun createIntent(context: Context, input: String?) =
    Intent(Intent.ACTION_PICK).apply { type = input }

  override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
    return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.data!!
  }
}

class LaunchAppDetailsSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
      data = Uri.fromParts("package", context.packageName, null)
    }

  override fun parseResult(resultCode: Int, intent: Intent?) {}
}

class EnableLocationContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) = isLocationEnabled
}

class EnableBluetoothContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    resultCode == Activity.RESULT_OK
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

fun interface AppSettingsScope {
  fun launchAppDetailsSetting()
}

fun interface PermissionScope {
  fun requestPermissionAgain()
}

fun interface PermissionsScope {
  fun requestPermissionsAgain()
}
