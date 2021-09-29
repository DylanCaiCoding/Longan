/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Dylan Cai
 */

inline val Int.dp: Float get() = toFloat().dp

inline val Long.dp: Float get() = toFloat().dp

inline val Double.dp: Float get() = toFloat().dp

inline val Float.dp: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline val Int.sp: Float get() = toFloat().sp

inline val Long.sp: Float get() = toFloat().sp

inline val Double.sp: Float get() = toFloat().sp

inline val Float.sp: Float
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

inline fun Int.pxToDp(): Int = toFloat().pxToDp()

inline fun Long.pxToDp(): Int = toFloat().pxToDp()

inline fun Double.pxToDp(): Int = toFloat().pxToDp()

inline fun Float.pxToDp(): Int =
  (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()

inline fun Int.pxToSp(): Int = toFloat().pxToSp()

inline fun Long.pxToSp(): Int = toFloat().pxToSp()

inline fun Double.pxToSp(): Int = toFloat().pxToSp()

inline fun Float.pxToSp(): Int =
  (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

