@file:Suppress("unused", "NOTHING_TO_INLINE", "FunctionName", "MemberVisibilityCanBePrivate")

package com.dylanc.grape

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
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
  ActivityResultLauncher(this::registerForActivityResult)

fun Fragment.ActivityResultLauncher() =
  ActivityResultLauncher(this::registerForActivityResult)

fun ComponentActivity.PermissionResultLauncher() =
  PermissionResultLauncher(
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this::shouldShowRequestPermissionRationale else null,
    this::registerForActivityResult
  )

fun Fragment.PermissionResultLauncher() =
  PermissionResultLauncher(
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this::shouldShowRequestPermissionRationale else null,
    this::registerForActivityResult
  )

fun ComponentActivity.MultiplePermissionsResultLauncher() =
  MultiplePermissionsResultLauncher(this::registerForActivityResult)

fun Fragment.MultiplePermissionsResultLauncher() =
  MultiplePermissionsResultLauncher(this::registerForActivityResult)

fun ComponentActivity.TakePicturePreviewResultLauncher() =
  TakePicturePreviewResultLauncher(this::registerForActivityResult)

fun Fragment.TakePicturePreviewResultLauncher() =
  TakePicturePreviewResultLauncher(this::registerForActivityResult)

fun ComponentActivity.TakePictureResultLauncher() =
  TakePictureResultLauncher(this::registerForActivityResult)

fun Fragment.TakePictureResultLauncher() =
  TakePictureResultLauncher(this::registerForActivityResult)

fun ComponentActivity.TakeVideoResultLauncher() =
  TakeVideoResultLauncher(this::registerForActivityResult)

fun Fragment.TakeVideoResultLauncher() =
  TakeVideoResultLauncher(this::registerForActivityResult)

fun ComponentActivity.PickContactResultLauncher() =
  PickContactResultLauncher(this::registerForActivityResult)

fun Fragment.PickContactResultLauncher() =
  PickContactResultLauncher(this::registerForActivityResult)

fun ComponentActivity.GetContentResultLauncher() =
  GetContentResultLauncher(this::registerForActivityResult)

fun Fragment.GetContentResultLauncher() =
  GetContentResultLauncher(this::registerForActivityResult)

fun ComponentActivity.CreateDocumentResultLauncher() =
  CreateDocumentResultLauncher(this::registerForActivityResult)

fun Fragment.CreateDocumentResultLauncher() =
  CreateDocumentResultLauncher(this::registerForActivityResult)

fun ComponentActivity.OpenMultipleDocumentsResultLauncher() =
  OpenMultipleDocumentsResultLauncher(this::registerForActivityResult)

fun Fragment.OpenMultipleDocumentsResultLauncher() =
  OpenMultipleDocumentsResultLauncher(this::registerForActivityResult)

fun ComponentActivity.OpenDocumentTreeResultLauncher() =
  OpenDocumentTreeResultLauncher(this::registerForActivityResult)

fun Fragment.OpenDocumentTreeResultLauncher() =
  OpenDocumentTreeResultLauncher(this::registerForActivityResult)

typealias RegisterForActivityResult<I, O> =
      (ActivityResultContract<I, O>, ActivityResultCallback<O>) -> androidx.activity.result.ActivityResultLauncher<I>

open class BaseLauncher<I, O>(
  registerForActivityResult: RegisterForActivityResult<I, O>,
  activityResultContract: ActivityResultContract<I, O>
) {
  private var onActivityResult: ((O) -> Unit)? = null

  private val launcher: androidx.activity.result.ActivityResultLauncher<I> =
    registerForActivityResult(activityResultContract) { result ->
      onActivityResult?.invoke(result)
      onActivityResult = null
    }

  fun launch(input: I, onActivityResult: (O) -> Unit) {
    launcher.launch(input)
    this.onActivityResult = onActivityResult
  }
}

class ActivityResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Intent, ActivityResult>
) :
  BaseLauncher<Intent, ActivityResult>(
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
  private val shouldShowRequestPermissionRationale: ((String) -> Boolean)?,
  registerForActivityResult: RegisterForActivityResult<String, Boolean>
) :
  BaseLauncher<String, Boolean>(registerForActivityResult, ActivityResultContracts.RequestPermission()) {

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
  BaseLauncher<Array<String>, Map<String, Boolean>>(
    registerForActivityResult,
    ActivityResultContracts.RequestMultiplePermissions()
  ) {

  fun launch(vararg permissions: String, onActivityResult: (Map<String, Boolean>) -> Unit) {
    launch(arrayOf(*permissions), onActivityResult)
  }
}

class TakePicturePreviewResultLauncher(registerForActivityResult: RegisterForActivityResult<Void, Bitmap>) :
  BaseLauncher<Void, Bitmap>(registerForActivityResult, ActivityResultContracts.TakePicturePreview())

class TakePictureResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Uri, Boolean>
) : BaseLauncher<Uri, Boolean>(registerForActivityResult, ActivityResultContracts.TakePicture()) {

  fun launch(onSuccess: (File, Uri) -> Unit, onFailure: () -> Unit = {}) {
    launch(path, onSuccess, onFailure)
  }

  fun launch(path: String, onSuccess: (File, Uri) -> Unit, onFailure: () -> Unit = {}) {
    val context = application
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      FileProvider.getUriForFile(context, "${context.packageName}.chooseProvider", File(path))
    } else {
      Uri.fromFile(File(path))
    }
    launch(uri) { takeSuccess ->
      if (takeSuccess) {
        onSuccess(uri.toFile(), uri)
      } else {
        onFailure()
      }
    }
  }

  val path: String
    get() = "${application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath}/${System.currentTimeMillis()}.jpg"
}

class TakeVideoResultLauncher(registerForActivityResult: RegisterForActivityResult<Uri, Bitmap>) :
  BaseLauncher<Uri, Bitmap>(registerForActivityResult, ActivityResultContracts.TakeVideo())

class PickContactResultLauncher(registerForActivityResult: RegisterForActivityResult<Void, Uri>) :
  BaseLauncher<Void, Uri>(registerForActivityResult, ActivityResultContracts.PickContact())

class GetContentResultLauncher(registerForActivityResult: RegisterForActivityResult<String, Uri>) :
  BaseLauncher<String, Uri>(registerForActivityResult, ActivityResultContracts.GetContent())

class CreateDocumentResultLauncher(registerForActivityResult: RegisterForActivityResult<String, Uri>) :
  BaseLauncher<String, Uri>(registerForActivityResult, ActivityResultContracts.CreateDocument())

class OpenMultipleDocumentsResultLauncher(registerForActivityResult: RegisterForActivityResult<Array<String>, List<Uri>>) :
  BaseLauncher<Array<String>, List<Uri>>(registerForActivityResult, ActivityResultContracts.OpenMultipleDocuments())

class OpenDocumentTreeResultLauncher(registerForActivityResult: RegisterForActivityResult<Uri, Uri>) :
  BaseLauncher<Uri, Uri>(registerForActivityResult, ActivityResultContracts.OpenDocumentTree())
