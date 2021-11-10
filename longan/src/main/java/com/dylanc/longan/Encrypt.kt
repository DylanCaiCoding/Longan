@file:Suppress("unused")

package com.dylanc.longan

import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString


fun String.encryptMD5(): String = encodeUtf8().md5().hex()

fun ByteArray.encryptMD5(): String = toByteString().md5().hex()

fun String.encryptSHA1(): String = encodeUtf8().sha1().hex()

fun ByteArray.encryptSHA1(): String = toByteString().sha1().hex()

fun String.encryptSHA256(): String = encodeUtf8().sha256().hex()

fun ByteArray.encryptSHA256(): String = toByteString().sha256().hex()

fun String.encryptSHA512(): String = encodeUtf8().sha512().hex()

fun ByteArray.encryptSHA512(): String = toByteString().sha512().hex()

fun String.encryptHmacSHA1(key: String): String = encodeUtf8().hmacSha1(key.encodeUtf8()).hex()

fun ByteArray.encryptHmacSHA1(key: String): String = toByteString().hmacSha1(key.encodeUtf8()).hex()

fun String.encryptHmacSHA256(key: String): String = encodeUtf8().hmacSha256(key.encodeUtf8()).hex()

fun ByteArray.encryptHmacSHA256(key: String): String = toByteString().hmacSha256(key.encodeUtf8()).hex()

fun String.encryptHmacSHA512(key: String): String = encodeUtf8().hmacSha512(key.encodeUtf8()).hex()

fun ByteArray.encryptHmacSHA512(key: String): String = toByteString().hmacSha512(key.encodeUtf8()).hex()
