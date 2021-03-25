@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import java.util.*

/**
 * @author Dylan Cai
 */

inline val randomUUIDString: String get() = UUID.randomUUID().toString()

inline fun Float.toPercentString() = "${this * 100}%"

inline fun Float.toIntPercentString() = "${(this * 100).toInt()}%"

inline fun Double.toPercentString() = "${this * 100}%"

inline fun Double.toIntPercentString() = "${(this * 100).toInt()}%"