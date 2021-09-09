@file:Suppress("NOTHING_TO_INLINE")

package com.dylanc.longan

import android.view.View

/**
 * @author Dylan Cai
 */

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw NotImplementedError(NO_GETTER)

internal var View.isAddedMarginTop: Boolean? by viewTags(-1001)
internal var View.isAddedPaddingTop: Boolean? by viewTags(-1002)
internal var View.isAddedMarginBottom: Boolean? by viewTags(-1003)
internal var View.lastClickTime: Long? by viewTags(-1004)
