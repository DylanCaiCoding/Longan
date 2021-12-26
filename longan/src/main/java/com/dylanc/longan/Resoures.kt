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

@file:Suppress("unused")

package com.dylanc.longan

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat


@ColorInt
fun getCompatColor(@ColorRes id: Int): Int =
  ResourcesCompat.getColor(application.resources, id, null)

fun getCompatDrawable(@DrawableRes id: Int): Drawable? =
  ResourcesCompat.getDrawable(application.resources, id, null)

fun getCompatFont(@FontRes id: Int): Typeface? =
  ResourcesCompat.getFont(application, id)

fun getString(@StringRes id: Int): String =
  application.getString(id)

fun getDimension(@DimenRes id: Int): Float =
  application.resources.getDimension(id)

@ColorInt
fun parseColor(colorString: String): Int =
  Color.parseColor(colorString)
