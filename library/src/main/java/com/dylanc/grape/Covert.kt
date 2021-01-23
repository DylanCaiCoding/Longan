@file:Suppress("unused")

package com.dylanc.grape

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Dylan Cai
 */

val Int.dp
  get() = toFloat().dp

val Float.dp
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)