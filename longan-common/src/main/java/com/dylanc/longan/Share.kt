@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.net.Uri
import androidx.core.app.ShareCompat

/**
 * @author Dylan Cai
 */

inline fun shareText(content: String, title: String? = null) =
  share("text/plain") {
    setText(content)
    setChooserTitle(title)
  }

inline fun shareImage(imageUri: Uri, title: String? = null) =
  shareTextAndImage(null, imageUri, title)

inline fun shareImages(imageUris: List<Uri>, title: String? = null) =
  shareTextAndImages(null, imageUris, title)

inline fun shareTextAndImage(content: String?, imageUri: Uri, title: String? = null) =
  share("image/*") {
    setText(content)
    setStream(imageUri)
    setChooserTitle(title)
  }

inline fun shareTextAndImages(content: String?, imageUris: List<Uri>, title: String? = null) =
  share("image/*") {
    setText(content)
    imageUris.forEach { addStream(it) }
    setChooserTitle(title)
  }

inline fun shareFile(uri: Uri, title: String? = null, mimeType: String = uri.mimeType.orEmpty()) =
  share(mimeType) {
    setStream(uri)
    setChooserTitle(title)
  }

inline fun shareFiles(uris: List<Uri>, title: String? = null, mimeType: String? = null) {
  val mime = mimeType ?: if (uris.isNotEmpty()) uris.first().mimeType.orEmpty() else ""
  share(mime) {
    uris.forEach { addStream(it) }
    setChooserTitle(title)
  }
}

inline fun share(mimeType: String, block: ShareCompat.IntentBuilder.() -> Unit) =
  ShareCompat.IntentBuilder(topActivity).apply { setType(mimeType) }.apply(block).startChooser()
