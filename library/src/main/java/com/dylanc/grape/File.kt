@file:Suppress("unused")

package com.dylanc.grape

import java.io.File
import java.net.URLConnection

/**
 * @author Dylan Cai
 */

inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)