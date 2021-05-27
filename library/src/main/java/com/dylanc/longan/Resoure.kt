@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * @author Dylan Cai
 */

inline fun Context.stringOf(@StringRes id: Int) = context.getString(id)

inline fun Context.colorOf(@ColorRes id: Int) = ContextCompat.getColor(context, id)

inline fun Context.drawableOf(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)

inline fun stringOf(@StringRes id: Int) = application.stringOf(id)

inline fun colorOf(@ColorRes id: Int) = application.colorOf(id)

inline fun drawableOf(@DrawableRes id: Int) = application.drawableOf(id)