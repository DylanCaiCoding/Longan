@file:Suppress("NOTHING_TO_INLINE")

package com.dylanc.longan

/**
 * @author Dylan Cai
 */

const val NO_GETTER: String = "Property does not have a getter"

inline fun noGetter(): Nothing = throw NotImplementedError(NO_GETTER)