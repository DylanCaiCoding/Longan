@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Dylan Cai
 */

inline val Int.dp get() = toFloat().dp

inline val Long.dp get() = toFloat().dp

inline val Double.dp get() = toFloat().dp

inline val Float.dp
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline val Int.sp get() = toFloat().sp

inline val Long.sp get() = toFloat().sp

inline val Double.sp get() = toFloat().sp

inline val Float.sp
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

inline fun Int.pxToDp() = toFloat().pxToDp()

inline fun Long.pxToDp() = toFloat().pxToDp()

inline fun Double.pxToDp() = toFloat().pxToDp()

inline fun Float.pxToDp() =
  (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()

inline fun Int.pxToSp() = toFloat().pxToSp()

inline fun Long.pxToSp() = toFloat().pxToSp()

inline fun Double.pxToSp() = toFloat().pxToSp()

inline fun Float.pxToSp() =
  (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

