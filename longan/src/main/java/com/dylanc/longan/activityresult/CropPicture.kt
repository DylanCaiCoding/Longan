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

package com.dylanc.longan.activityresult

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.dylanc.longan.contentResolver
import com.dylanc.longan.grantReadUriPermission
import com.dylanc.longan.insertMediaImage

fun ActivityResultCaller.registerForCropPictureResult(callback: ActivityResultCallback<Uri>) =
  registerForActivityResult(CropPictureContract(), callback)

class CropPictureRequest constructor(
  val inputUri: Uri,
  val outputUri: Uri? = null,
  val extras: Bundle = Bundle()
)

open class CropPictureContract : ActivityResultContract<CropPictureRequest, Uri>() {
  private var outputUri: Uri? = null

  @CallSuper
  override fun createIntent(context: Context, input: CropPictureRequest): Intent {
    outputUri = input.outputUri ?: contentResolver.insertMediaImage()
    return Intent("com.android.camera.action.CROP")
      .setDataAndType(input.inputUri, "image/*")
      .putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
      .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
      .putExtra("return-data", false)
      .putExtras(input.extras)
      .grantReadUriPermission()
  }

  override fun parseResult(resultCode: Int, intent: Intent?) = outputUri
}
