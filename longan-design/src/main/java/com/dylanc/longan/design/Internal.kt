package com.dylanc.longan.design

/**
 * @author Dylan Cai
 */

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw NotImplementedError(NO_GETTER)
