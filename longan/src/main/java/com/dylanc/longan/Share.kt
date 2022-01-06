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

import android.net.Uri
import androidx.core.app.ShareCompat


fun shareText(content: String, title: String? = null) =
  share("text/plain") {
    setText(content)
    setChooserTitle(title)
  }

fun shareImage(imageUri: Uri, title: String? = null) =
  shareTextAndImage(null, imageUri, title)

fun shareImages(imageUris: List<Uri>, title: String? = null) =
  shareTextAndImages(null, imageUris, title)

fun shareTextAndImage(content: String?, imageUri: Uri, title: String? = null) =
  share("image/*") {
    setText(content)
    setStream(imageUri)
    setChooserTitle(title)
  }

fun shareTextAndImages(content: String?, imageUris: List<Uri>, title: String? = null) =
  share("image/*") {
    setText(content)
    imageUris.forEach { addStream(it) }
    setChooserTitle(title)
  }

fun shareFile(uri: Uri, title: String? = null, mimeType: String = uri.mimeType.orEmpty()) =
  share(mimeType) {
    setStream(uri)
    setChooserTitle(title)
  }

fun shareFiles(uris: List<Uri>, title: String? = null, mimeType: String? = null) =
  share(mimeType ?: uris.firstOrNull()?.mimeType) {
    uris.forEach { addStream(it) }
    setChooserTitle(title)
  }

inline fun share(mimeType: String?, crossinline block: ShareCompat.IntentBuilder.() -> Unit) =
  ShareCompat.IntentBuilder(topActivity).setType(mimeType).apply(block).startChooser()
