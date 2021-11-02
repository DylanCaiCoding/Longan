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

import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder


fun ByteArray.base64Encode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.encode(this, flag)

fun ByteArray.base64EncodeToString(flag: Int = Base64.DEFAULT): String =
  Base64.encodeToString(this, flag)

fun String.base64Decode(flag: Int = Base64.DEFAULT): ByteArray =
  Base64.decode(this, flag)

fun String.urlEncode(enc: String): String =
  URLEncoder.encode(this, enc)

fun String.urlDecode(enc: String): String =
  URLDecoder.decode(this, enc)
