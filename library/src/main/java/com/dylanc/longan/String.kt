@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.util.Patterns
import java.util.*

/**
 * @author Dylan Cai
 */

inline val randomUUIDString: String get() = UUID.randomUUID().toString()

inline fun Float.toPercentString() = "${this * 100}%"

inline fun Float.toIntPercentString() = "${(this * 100).toInt()}%"

inline fun Double.toPercentString() = "${this * 100}%"

inline fun Double.toIntPercentString() = "${(this * 100).toInt()}%"

inline fun String.isPhone() = Patterns.PHONE.matcher(this).matches()

inline fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

inline fun String.limitLength(length: Int) = if (this.length <= length) this else substring(0, length)