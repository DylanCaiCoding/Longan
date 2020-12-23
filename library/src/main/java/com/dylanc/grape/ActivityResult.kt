@file:Suppress("unused", "NOTHING_TO_INLINE", "FunctionName")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

fun ComponentActivity.ActivityResultLauncher() = ActivityResultLauncher({ this }, this::registerForActivityResult)

fun Fragment.ActivityResultLauncher() = ActivityResultLauncher({ requireContext() }, this::registerForActivityResult)

fun ComponentActivity.PermissionResultLauncher() = PermissionResultLauncher(this::registerForActivityResult)

fun Fragment.PermissionResultLauncher() = PermissionResultLauncher(this::registerForActivityResult)

fun ComponentActivity.MultiplePermissionsResultLauncher() = MultiplePermissionsResultLauncher(this::registerForActivityResult)

fun Fragment.MultiplePermissionsResultLauncher() = MultiplePermissionsResultLauncher(this::registerForActivityResult)

typealias RegisterForActivityResult<I, O> =
      (ActivityResultContract<I, O>, ActivityResultCallback<O>) -> androidx.activity.result.ActivityResultLauncher<I>

open class BaseLauncher<I, O>(
  registerForActivityResult: RegisterForActivityResult<I, O>,
  activityResultContract: ActivityResultContract<I, O>
) {
  private var observer: ((O) -> Unit)? = null

  private val launcher: androidx.activity.result.ActivityResultLauncher<I> =
    registerForActivityResult(activityResultContract) { result ->
      observer?.invoke(result)
      observer = null
    }

  fun launch(input: I, observer: (O) -> Unit) {
    launcher.launch(input)
    this.observer = observer
  }
}

class ActivityResultLauncher(
  private val getContext: () -> Context,
  registerForActivityResult: RegisterForActivityResult<Intent, ActivityResult>
) :
  BaseLauncher<Intent, ActivityResult>(registerForActivityResult, ActivityResultContracts.StartActivityForResult()) {

  fun <T : Activity> launch(clazz: Class<T>, block: (ActivityResult) -> Unit) {
    launch(Intent(getContext(), clazz), block)
  }

  inline fun <reified T : Activity> launch(noinline block: (ActivityResult) -> Unit) {
    launch(T::class.java, block)
  }
}

class PermissionResultLauncher(
  registerForActivityResult: RegisterForActivityResult<String, Boolean>
) :
  BaseLauncher<String, Boolean>(registerForActivityResult, ActivityResultContracts.RequestPermission())

class MultiplePermissionsResultLauncher(
  registerForActivityResult: RegisterForActivityResult<Array<String>, Map<String, Boolean>>
) :
  BaseLauncher<Array<String>, Map<String, Boolean>>(registerForActivityResult, ActivityResultContracts.RequestMultiplePermissions())

