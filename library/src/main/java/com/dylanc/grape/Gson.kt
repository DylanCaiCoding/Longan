@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import org.json.JSONObject

/**
 * @author Dylan Cai
 */


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