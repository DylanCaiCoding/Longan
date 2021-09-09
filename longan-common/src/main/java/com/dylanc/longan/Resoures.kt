@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.graphics.Color
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat

/**
 * @author Dylan Cai
 */

inline fun stringRes(@StringRes id: Int) = topActivity.stringRes(id)

inline fun colorRes(@ColorRes id: Int) = topActivity.colorRes(id)

inline fun drawableRes(@DrawableRes id: Int) = topActivity.drawableRes(id)

inline fun dimenRes(@DimenRes id: Int) = topActivity.dimenRes(id)

inline fun fontRes(@FontRes id: Int) = topActivity.fontRes(id)

inline fun Context.stringRes(@StringRes id: Int) = lazy {
  getString(id)
}

inline fun Context.colorRes(@ColorRes id: Int) = lazy {
  ResourcesCompat.getColor(application.resources, id, null)
}

inline fun Context.drawableRes(@DrawableRes id: Int) = lazy {
  ResourcesCompat.getDrawable(application.resources, id, null)
}

inline fun Context.dimenRes(@DimenRes id: Int) = lazy {
  ResourcesCompat.getFloat(application.resources, id)
}

inline fun Context.fontRes(@FontRes id: Int) = lazy {
  ResourcesCompat.getFont(this, id)
}

inline fun colorOf(colorString: String) = Color.parseColor(colorString)
