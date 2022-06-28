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

package com.dylanc.longan.sample.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dylanc.longan.sample.databinding.ActivityPictureBinding
import com.dylanc.longan.startActivity
import com.dylanc.viewbinding.binding

class PictureActivity : AppCompatActivity() {
  private val binding: ActivityPictureBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    intent.getParcelableExtra<Bitmap>("bitmap")?.let {
      binding.imageView.setImageBitmap(it)
    }
    intent.getStringExtra("uri")?.let {
      Glide.with(this).load(Uri.parse(it)).into(binding.imageView)
    }
  }

  companion object {
    fun start(uri: Uri) = startActivity<PictureActivity>("uri" to uri.toString())

    fun start(bitmap: Bitmap) = startActivity<PictureActivity>("bitmap" to bitmap)
  }
}