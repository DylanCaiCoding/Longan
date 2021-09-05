@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.net.URLConnection

/**
 * @author Dylan Cai
 */

inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)

inline val fileSeparator: String get() = File.separator

inline fun File.print(block: PrintWriter.() -> Unit) =
  PrintWriter(BufferedWriter(FileWriter(this))).apply(block).close()
