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

import okio.ByteString.Companion.encodeUtf8
import okio.HashingSink
import okio.blackholeSink
import okio.buffer
import okio.source
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.net.URLConnection


inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)

inline val fileSeparator: String get() = File.separator

inline fun File.print(crossinline block: PrintWriter.() -> Unit) =
  PrintWriter(BufferedWriter(FileWriter(this))).apply(block).close()

fun File.checkMD5(md5: String): Boolean = calculateMD5().equals(md5, true)

fun File.checkSHA1(sha1: String): Boolean = calculateSHA1().equals(sha1, true)

fun File.checkSHA256(sha256: String): Boolean = calculateSHA256().equals(sha256, true)

fun File.checkSHA512(sha512: String): Boolean = calculateSHA512().equals(sha512, true)

fun File.checkHmacSHA1(key: String, hmacSHA1: String): Boolean =
  calculateHmacSHA1(key).equals(hmacSHA1, true)

fun File.checkHmacSHA256(key: String, hmacSHA256: String): Boolean =
  calculateHmacSHA256(key).equals(hmacSHA256, true)

fun File.checkHmacSHA512(key: String, hmacSHA512: String): Boolean =
  calculateHmacSHA512(key).equals(hmacSHA512, true)

fun File.calculateMD5(): String = calculateHash(HashingSink.md5(blackholeSink()))

fun File.calculateSHA1(): String = calculateHash(HashingSink.sha1(blackholeSink()))

fun File.calculateSHA256(): String = calculateHash(HashingSink.sha256(blackholeSink()))

fun File.calculateSHA512(): String = calculateHash(HashingSink.sha512(blackholeSink()))

fun File.calculateHmacSHA1(key: String): String =
  calculateHash(HashingSink.hmacSha1(blackholeSink(), key.encodeUtf8()))

fun File.calculateHmacSHA256(key: String): String =
  calculateHash(HashingSink.hmacSha256(blackholeSink(), key.encodeUtf8()))

fun File.calculateHmacSHA512(key: String): String =
  calculateHash(HashingSink.hmacSha512(blackholeSink(), key.encodeUtf8()))

private fun File.calculateHash(hashingSink: HashingSink) =
  hashingSink.use {
    source().buffer().use { source ->
      source.readAll(hashingSink)
      hashingSink.hash.hex()
    }
  }
