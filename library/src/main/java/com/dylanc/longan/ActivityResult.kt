@file:Suppress("unused", "NOTHING_TO_INLINE", "MemberVisibilityCanBePrivate", "FunctionName")

package com.dylanc.longan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.annotation.CallSuper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.io.File

/**
 * @author Dylan Cai
 */

fun ComponentActivity.ActivityResultLauncher() = ActivityResultLauncher(this)

fun Fragment.ActivityResultLauncher() = ActivityResultLauncher(this)

fun <I, O> ComponentActivity.ActivityResultLauncher(activityResultContract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, activityResultContract)

fun <I, O> Fragment.ActivityResultLauncher(activityResultContract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, activityResultContract)

fun ComponentActivity.PermissionLauncher() = PermissionLauncher(this)

fun Fragment.PermissionLauncher() = PermissionLauncher(this)

fun ComponentActivity.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

fun Fragment.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

fun ComponentActivity.TakePicturePreviewLauncher() = TakePicturePreviewLauncher(this)

fun Fragment.TakePicturePreviewLauncher() = TakePicturePreviewLauncher(this)

fun ComponentActivity.TakePictureLauncher() = TakePictureLauncher(this)

fun Fragment.TakePictureLauncher() = TakePictureLauncher(this)

fun ComponentActivity.TakeVideoLauncher() = TakeVideoLauncher(this)

fun Fragment.TakeVideoLauncher() = TakeVideoLauncher(this)

fun ComponentActivity.PickContactLauncher() = PickContactLauncher(this)

fun Fragment.PickContactLauncher() = PickContactLauncher(this)

fun ComponentActivity.GetContentLauncher() = GetContentLauncher(this)

fun Fragment.GetContentLauncher() = GetContentLauncher(this)

fun ComponentActivity.CreateDocumentLauncher() = CreateDocumentLauncher(this)

fun Fragment.CreateDocumentLauncher() = CreateDocumentLauncher(this)

fun ComponentActivity.OpenMultipleDocumentsLauncher() = OpenMultipleDocumentsLauncher(this)

fun Fragment.OpenMultipleDocumentsLauncher() = OpenMultipleDocumentsLauncher(this)

fun ComponentActivity.OpenDocumentTreeLauncher() = OpenDocumentTreeLauncher(this)

fun Fragment.OpenDocumentTreeLauncher() = OpenDocumentTreeLauncher(this)

fun ComponentActivity.CropPictureLauncher() = CropPictureLauncher(this)

fun Fragment.CropPictureLauncher() = CropPictureLauncher(this)

open class BaseActivityResultLauncher<I, O>(
  activityResultCaller: ActivityResultCaller,
  activityResultContract: ActivityResultContract<I, O>
) {
  private var onActivityResult: ((O?) -> Unit)? = null

  private val launcher =
    activityResultCaller.registerForActivityResult(activityResultContract) { result ->
      onActivityResult?.invoke(result)
      onActivityResult = null
    }

  open fun launch(input: I?, onActivityResult: (O) -> Unit) =
    launchForNullableResult(input, { if (it != null) onActivityResult(it) })

  open fun launchForNullableResult(input: I?, onActivityResult: (O?) -> Unit) {
    this.onActivityResult = onActivityResult
    launcher.launch(input)
  }
}

class ActivityResultLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Intent, ActivityResult>(activityResultCaller, StartActivityForResult()) {

  inline fun <reified T : Activity> launch(noinline onActivityResult: (ActivityResult) -> Unit) {
    launch(T::class.java, onActivityResult)
  }

  fun <T : Activity> launch(clazz: Class<T>, onActivityResult: (ActivityResult) -> Unit) {
    launch(Intent(application, clazz), onActivityResult)
  }
}

class PermissionLauncher(
  activityResultCaller: ActivityResultCaller,
  private val shouldShowRequestPermissionRationale: (String) -> Boolean
) : BaseActivityResultLauncher<String, Boolean>(activityResultCaller, RequestPermission()) {

  constructor(activity: ComponentActivity) :
      this(activity, { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) })

  constructor(fragment: Fragment) : this(
    fragment, {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        fragment.shouldShowRequestPermissionRationale(it)
      else false
    }
  )

  fun launch(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
  ) {
    launch(permission) {
      when {
        it -> onGranted()
        shouldShowRequestPermissionRationale(permission) -> onShowRationale()
        else -> onDenied()
      }
    }
  }
}

class MultiplePermissionsLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, Map<String, Boolean>>(activityResultCaller, RequestMultiplePermissions()) {

  fun launch(vararg permissions: String, onActivityResult: (Map<String, Boolean>) -> Unit) {
    launch(arrayOf(*permissions), onActivityResult)
  }
}

class TakePicturePreviewLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Void, Bitmap>(activityResultCaller, TakePicturePreview()) {

  fun launch(onActivityResult: (Bitmap) -> Unit) = launch(null, onActivityResult)
}

class TakePictureLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Boolean>(activityResultCaller, TakePicture()) {

  fun launch(onTakeSuccess: (Uri, String) -> Unit) {
    val pathname = "$externalCacheDirPath/${System.currentTimeMillis()}.jpg"
    launch(File(pathname)) { uri ->
      onTakeSuccess(uri, pathname)
    }
  }

  fun launch(pathname: String, onTakeSuccess: (Uri) -> Unit) {
    launch(File(pathname), onTakeSuccess)
  }

  fun launch(file: File, onTakeSuccess: (Uri) -> Unit) =
    file.toUri().let {
      launch(it) { takeSuccess ->
        if (takeSuccess)
          onTakeSuccess(it)
      }
    }
}

class TakeVideoLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Bitmap>(activityResultCaller, TakeVideo()) {

  fun launch(onTakeSuccess: (Uri, String, Bitmap?) -> Unit) {
    val pathname = "$externalCacheDirPath/${System.currentTimeMillis()}.mp4"
    launch(File(pathname)) { uri, bitmap ->
      onTakeSuccess(uri, pathname, bitmap)
    }
  }

  fun launch(pathname: String, onTakeSuccess: (Uri, Bitmap?) -> Unit) {
    launch(File(pathname), onTakeSuccess)
  }

  fun launch(file: File, onTakeSuccess: (Uri, Bitmap?) -> Unit) =
    file.toUri().let { uri ->
      launchForNullableResult(uri) {
        onTakeSuccess(uri, it)
      }
    }
}

class PickContactLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Void, Uri>(activityResultCaller, PickContact()) {

  fun launch(onActivityResult: (Uri) -> Unit) = launch(null, onActivityResult)
}

class GetContentLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, Uri>(activityResultCaller, GetContent()) {

  fun launchForImage(onActivityResult: (Uri) -> Unit) = launch("image/*", onActivityResult)

  fun launchForVideo(onActivityResult: (Uri) -> Unit) = launch("video/*", onActivityResult)

  fun launchForAudio(onActivityResult: (Uri) -> Unit) = launch("audio/*", onActivityResult)
}

class GetMultipleContentLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, List<Uri>>(activityResultCaller, GetMultipleContents()) {

  fun launchForImage(onActivityResult: (List<Uri>) -> Unit) = launch("image/*", onActivityResult)

  fun launchForVideo(onActivityResult: (List<Uri>) -> Unit) = launch("video/*", onActivityResult)

  fun launchForAudio(onActivityResult: (List<Uri>) -> Unit) = launch("audio/*", onActivityResult)
}

class CreateDocumentLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, Uri>(activityResultCaller, CreateDocument())

class OpenMultipleDocumentsLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, List<Uri>>(activityResultCaller, OpenMultipleDocuments())

class OpenDocumentTreeLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Uri>(activityResultCaller, OpenDocumentTree())

class CropPictureLauncher(activityResultCaller: ActivityResultCaller) :
  BaseActivityResultLauncher<CropPictureConfig, Uri>(activityResultCaller, CropPictureContract()) {

  fun launch(
    inputUri: Uri,
    outputUri: Uri = insertMediaImageUri()!!,
    aspectX: Int = 1,
    aspectY: Int = 1,
    outputX: Int = 512,
    outputY: Int = 512,
    isScale: Boolean = true,
    isScaleUpIfNeeded: Boolean = true,
    block: (Uri) -> Unit
  ) =
    launch(
      CropPictureConfig(inputUri, aspectX, aspectY, outputX, outputY, isScale, isScaleUpIfNeeded, outputUri), block
    )
}

data class CropPictureConfig(
  val inputUri: Uri,
  val aspectX: Int = 1,
  val aspectY: Int = 1,
  val outputX: Int = 512,
  val outputY: Int = 512,
  val isScale: Boolean = true,
  val isScaleUpIfNeeded: Boolean = false,
  val outputUri: Uri = insertMediaImageUri()!!
)

class CropPictureContract : ActivityResultContract<CropPictureConfig, Uri>() {
  private lateinit var outputUri: Uri

  @CallSuper
  override fun createIntent(context: Context, input: CropPictureConfig): Intent {
    return Intent("com.android.camera.action.CROP")
      .apply {
        setDataAndType(input.inputUri, "image/*")
        putExtra(MediaStore.EXTRA_OUTPUT, input.outputUri)
        putExtra("aspectX", input.aspectX)
        putExtra("aspectY", input.aspectY)
        putExtra("outputX", input.outputX)
        putExtra("outputY", input.outputY)
        putExtra("scale", input.isScale)
        putExtra("scaleUpIfNeeded", input.isScaleUpIfNeeded)
        putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        putExtra("return-data", false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        outputUri = input.outputUri
      }
  }

  override fun getSynchronousResult(
    context: Context,
    input: CropPictureConfig
  ): SynchronousResult<Uri>? {
    return null
  }

  override fun parseResult(resultCode: Int, intent: Intent?): Uri {
    return outputUri
  }
}
