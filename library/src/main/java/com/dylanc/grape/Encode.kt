@file:Suppress("unused")

package com.dylanc.grape

import android.util.Base64

/**
 * @author Dylan Cai
 */

fun ByteArray.base64EncodeToString(flag: Int = Base64.DEFAULT): String =
  Base64.encodeToString(this, flag)

fun ByteArray.base64Encode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.encode(this, flag)

fun String.base64Decode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.decode(this, flag)