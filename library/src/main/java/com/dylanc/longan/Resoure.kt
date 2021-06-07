@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

/**
 * @author Dylan Cai
 */

inline fun Context.getCompatColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)

inline fun Context.getCompatDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

inline fun stringOf(@StringRes id: Int) = topActivity.getString(id)

inline fun colorOf(@ColorRes id: Int) = topActivity.getCompatColor(id)

inline fun colorOf(colorString: String) = Color.parseColor(colorString)

inline fun drawableOf(@DrawableRes id: Int) = topActivity.getCompatDrawable(id)

inline fun fontOf(@FontRes id: Int) = ResourcesCompat.getFont(topActivity, id)