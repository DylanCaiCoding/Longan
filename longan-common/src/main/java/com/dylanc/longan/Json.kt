@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

/**
 * @author Dylan Cai
 */

inline fun <reified T> String.toInstance() = Json.decodeFromString<T>(this)

inline fun <reified T> Any.toJson(): String = Json.encodeToString(this)

/**
 * Returns the string is a JSON string.
 */
inline fun String.isJson(): Boolean =
  try {
    JSONObject(this)
    true
  } catch (e: Exception) {
    false
  }
