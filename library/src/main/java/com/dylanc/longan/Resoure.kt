@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * @author Dylan Cai
 */

inline fun colorOf(@ColorRes id: Int) = ContextCompat.getColor(application, id)

inline fun drawableOf(@DrawableRes id: Int) = ContextCompat.getDrawable(application, id)