@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * @author Dylan Cai
 */

inline fun ByteArray.base64Encode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.encode(this, flag)

inline fun ByteArray.base64EncodeToString(flag: Int = Base64.DEFAULT): String =
  Base64.encodeToString(this, flag)

inline fun String.base64Decode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.decode(this, flag)

inline fun String.urlEncode(enc: String): String =
  URLEncoder.encode(this, enc)

inline fun String.urlDecode(enc: String): String =
  URLDecoder.decode(this, enc)