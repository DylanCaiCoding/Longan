@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.net.Uri
import androidx.core.app.ShareCompat

/**
 * @author Dylan Cai
 */

inline fun share(mimeType: String, block: ShareCompat.IntentBuilder.() -> Unit) =
  ShareCompat.IntentBuilder(topActivity).apply { setType(mimeType) }.apply(block).startChooser()

inline fun shareText(content: String, title: String? = null) =
  share("text/plain") {
    setText(content)
    setChooserTitle(title)
  }

inline fun shareImage(imageUri: Uri, title: String? = null) =
  shareTextImage(null, imageUri, title)

inline fun shareTextImage(content: String?, imageUri: Uri, title: String? = null) =
  share("image/*") {
    setText(content)
    setStream(imageUri)
    setChooserTitle(title)
  }

inline fun shareTextImage(content: String?, imageUris: List<Uri>, title: String? = null) =
  share("image/*") {
    setText(content)
    imageUris.forEach { addStream(it) }
    setChooserTitle(title)
  }

inline fun shareFile(uri: Uri, mimeType: String = uri.mimeType.orEmpty(), title: String? = null) =
  share(mimeType) {
    setStream(uri)
    setChooserTitle(title)
  }

inline fun shareFiles(uris: List<Uri>, title: String? = null) {
  if (uris.isNotEmpty()) {
    shareFiles(uris, uris[0].mimeType.orEmpty(), title)
  }
}

inline fun shareFiles(uris: List<Uri>, mimeType: String, title: String? = null) =
  share(mimeType) {
    uris.forEach { addStream(it) }
    setChooserTitle(title)
  }