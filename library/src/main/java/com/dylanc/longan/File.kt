@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.text.format.Formatter
import java.io.File
import java.net.URLConnection

/**
 * @author Dylan Cai
 */

inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)

inline val fileSeparator: String get() = File.separator

inline fun Long.toFileSizeString(): String = Formatter.formatFileSize(application, this)

inline fun Long.toShortFileSizeString(): String = Formatter.formatShortFileSize(application, this)