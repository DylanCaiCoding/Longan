package com.dylanc.grape

import android.os.Bundle

/**
 * @author Dylan Cai
 */

inline operator fun <reified T> Bundle?.get(key: String): T? {
  val value = this?.get(key)
  return if (value is T) value else null
}