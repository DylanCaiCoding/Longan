@file:Suppress("unused", "NOTHING_TO_INLINE", "FunctionName", "MemberVisibilityCanBePrivate")

package com.dylanc.grape

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

/**
 * @author Dylan Cai
 */

fun ComponentActivity.ActivityResultLauncher() =
  ActivityResultLauncher(::registerForActivityResult)

fun Fragment.ActivityResultLauncher() =
  ActivityResultLauncher(::registerForActivityResult)

fun ComponentActivity.PermissionResultLauncher() =
  PermissionResultLauncher(
    ::registerForActivityResult,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ::shouldShowRequestPermissionRationale else null
  )

fun Fragment.PermissionResultLauncher() =
  PermissionResultLauncher(
    ::registerForActivityResult,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ::shouldShowRequestPermissionRationale else null
  )

fun ComponentActivity.MultiplePermissionsResultLauncher() =
  MultiplePermissionsResultLauncher(::registerForActivityResult)

fun Fragment.MultiplePermissionsResultLauncher() =
  MultiplePermissionsResultLauncher(::registerForActivityResult)

fun ComponentActivity.TakePicturePreviewResultLauncher() =
  TakePicturePreviewResultLauncher(::registerForActivityResult)

fun Fragment.TakePicturePreviewResultLauncher() =
  TakePicturePreviewResultLauncher(::registerForActivityResult)

fun ComponentActivity.TakePictureResultLauncher() =
  TakePictureResultLauncher(::registerForActivityResult)

fun Fragment.TakePictureResultLauncher() =
  TakePictureResultLauncher(::registerForActivityResult)

fun ComponentActivity.TakeVideoResultLauncher() =
  TakeVideoResultLauncher(::registerForActivityResult)

fun Fragment.TakeVideoResultLauncher() =
  TakeVideoResultLauncher(::registerForActivityResult)

fun ComponentActivity.PickContactResultLauncher() =
  PickContactResultLauncher(::registerForActivityResult)

fun Fragment.PickContactResultLauncher() =
  PickContactResultLauncher(::registerForActivityResult)

fun ComponentActivity.GetContentResultLauncher() =
  GetContentResultLauncher(::registerForActivityResult)

fun Fragment.GetContentResultLauncher() =
  GetContentResultLauncher(::registerForActivityResult)

fun ComponentActivity.CreateDocumentResultLauncher() =
  CreateDocumentResultLauncher(::registerForActivityResult)

fun Fragment.CreateDocumentResultLauncher() =
  CreateDocumentResultLauncher(::registerForActivityResult)

fun ComponentActivity.OpenMultipleDocumentsResultLauncher() =
  OpenMultipleDocumentsResultLauncher(::registerForActivityResult)

fun Fragment.OpenMultipleDocumentsResultLauncher() =
  OpenMultipleDocumentsResultLauncher(::registerForActivityResult)

fun ComponentActivity.OpenDocumentTreeResultLauncher() =
  OpenDocumentTreeResultLauncher(::registerForActivityResult)

fun Fragment.OpenDocumentTreeResultLauncher() =
  OpenDocumentTreeResultLauncher(::registerForActivityResult)

typealias RegisterForActivityResult<I, O> =
      (ActivityResultContract<I, O>, ActivityResultCallback<O>) -> androidx.activity.result.ActivityResultLauncher<I>

open class BaseResultLauncher<I, O>(
  registerForActivityResult: RegisterForActivityResult<I, O>,
  activityResultContract: ActivityResultContract<I, O>
) {
  private var onActivityResult: ((O?) -> Unit)? = null

  private val launcher =
    registerForActivityResult(activityResultContract) { result ->
      onActivityResult?.invoke(result)
      onActivityResult = null
    }

  fun launch(input: I, onActivityResult: (O) -> Unit) =
    launchForNullableResult(input, { if (it != null) onActivityResult(it) })

  fun launchForNullableResult(input: I, onActivityResult: (O?) -> Unit) {
    launcher.launch(input)
    this.onActivityResult = onActivityResult
  }
}

class ActivityResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Intent, ActivityResult>
) : BaseResultLauncher<Intent, ActivityResult>(
  registerForActivityResult,
  ActivityResultContracts.StartActivityForResult()
) {

  fun <T : Activity> launch(clazz: Class<T>, onActivityResult: (ActivityResult) -> Unit) {
    launch(Intent(application, clazz), onActivityResult)
  }

  inline fun <reified T : Activity> launch(noinline onActivityResult: (ActivityResult) -> Unit) {
    launch(T::class.java, onActivityResult)
  }
}

class PermissionResultLauncher(
  registerForActivityResult: RegisterForActivityResult<String, Boolean>,
  private val shouldShowRequestPermissionRationale: ((String) -> Boolean)?
) :
  BaseResultLauncher<String, Boolean>(registerForActivityResult, ActivityResultContracts.RequestPermission()) {

  fun launch(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
  ) {
    launch(permission) {
      when {
        it -> onGranted()
        shouldShowRequestPermissionRationale?.invoke(permission) == true -> onShowRationale()
        else -> onDenied()
      }
    }
  }
}

class MultiplePermissionsResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Array<String>, Map<String, Boolean>>
) :
  BaseResultLauncher<Array<String>, Map<String, Boolean>>(
    registerForActivityResult,
    ActivityResultContracts.RequestMultiplePermissions()
  ) {

  fun launch(vararg permissions: String, onActivityResult: (Map<String, Boolean>) -> Unit) {
    launch(arrayOf(*permissions), onActivityResult)
  }
}

class TakePicturePreviewResultLauncher(registerForActivityResult: RegisterForActivityResult<Void, Bitmap>) :
  BaseResultLauncher<Void, Bitmap>(registerForActivityResult, ActivityResultContracts.TakePicturePreview())

class TakePictureResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Uri, Boolean>
) : BaseResultLauncher<Uri, Boolean>(registerForActivityResult, ActivityResultContracts.TakePicture()) {

  fun launch(onSuccess: (String, Uri) -> Unit, onFailure: () -> Unit = {}) {
    launch(pathname, onSuccess, onFailure)
  }

  fun launch(pathname: String, onSuccess: (String, Uri) -> Unit, onFailure: () -> Unit = {}) {
    launch(File(pathname), onSuccess, onFailure)
  }

  fun launch(file: File, onSuccess: (String, Uri) -> Unit, onFailure: () -> Unit = {}) {
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      FileProvider.getUriForFile(application, "${packageName}.provider", file)
    } else {
      Uri.fromFile(file)
    }
    launch(uri) { takeSuccess ->
      if (takeSuccess) {
        onSuccess(file.path, uri)
      } else {
        onFailure()
      }
    }
  }

  val pathname: String get() = "$externalCacheDirPath/${System.currentTimeMillis()}.jpg"
}

class TakeVideoResultLauncher(registerForActivityResult: RegisterForActivityResult<Uri, Bitmap>) :
  BaseResultLauncher<Uri, Bitmap>(registerForActivityResult, ActivityResultContracts.TakeVideo())

class PickContactResultLauncher(registerForActivityResult: RegisterForActivityResult<Void, Uri>) :
  BaseResultLauncher<Void, Uri>(registerForActivityResult, ActivityResultContracts.PickContact())

class GetContentResultLauncher(registerForActivityResult: RegisterForActivityResult<String, Uri>) :
  BaseResultLauncher<String, Uri>(registerForActivityResult, ActivityResultContracts.GetContent())

class CreateDocumentResultLauncher(registerForActivityResult: RegisterForActivityResult<String, Uri>) :
  BaseResultLauncher<String, Uri>(registerForActivityResult, ActivityResultContracts.CreateDocument())

class OpenMultipleDocumentsResultLauncher(registerForActivityResult: RegisterForActivityResult<Array<String>, List<Uri>>) :
  BaseResultLauncher<Array<String>, List<Uri>>(registerForActivityResult, ActivityResultContracts.OpenMultipleDocuments())

class OpenDocumentTreeResultLauncher(registerForActivityResult: RegisterForActivityResult<Uri, Uri>) :
  BaseResultLauncher<Uri, Uri>(registerForActivityResult, ActivityResultContracts.OpenDocumentTree())
