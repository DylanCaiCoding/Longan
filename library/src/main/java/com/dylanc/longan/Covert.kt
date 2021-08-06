@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Dylan Cai
 */

inline val Long.dp get() = toFloat().dp

inline val Int.dp get() = toFloat().dp

inline val Double.dp get() = toFloat().dp

inline val Float.dp
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)