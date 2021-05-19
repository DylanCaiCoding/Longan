package com.dylanc.longan

import android.os.Bundle

/**
 * @author Dylan Cai
 */

inline operator fun <reified T> Bundle?.get(key: String): T? =
  this?.get(key).let { if (it is T) it else null }