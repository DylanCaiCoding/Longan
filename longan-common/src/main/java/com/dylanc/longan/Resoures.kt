@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat

/**
 * @author Dylan Cai
 */

inline fun View.stringRes(@StringRes id: Int) = context.stringRes(id)

inline fun Context.stringRes(@StringRes id: Int) = lazy { getString(id) }

inline fun View.colorRes(@ColorRes id: Int) = context.colorRes(id)

inline fun Context.colorRes(@ColorRes id: Int) = lazy { getCompatColor(id) }

inline fun View.drawableRes(@DrawableRes id: Int) = context.drawableRes(id)

inline fun Context.drawableRes(@DrawableRes id: Int) = lazy { getCompatDrawable(id) }

inline fun View.dimenRes(@DimenRes id: Int) = context.dimenRes(id)

inline fun Context.dimenRes(@DimenRes id: Int) = lazy { getCompatDimen(id) }

inline fun View.fontRes(@FontRes id: Int) = context.fontRes(id)

inline fun Context.fontRes(@FontRes id: Int) = lazy { getCompatFont(id) }

inline fun View.getCompatColor(@ColorRes id: Int) =
  context.getCompatColor(id)

inline fun Context.getCompatColor(@ColorRes id: Int) =
  ResourcesCompat.getColor(resources, id, null)

inline fun View.getCompatDrawable(@DrawableRes id: Int) =
  context.getCompatDrawable(id)

inline fun Context.getCompatDrawable(@DrawableRes id: Int) =
  ResourcesCompat.getDrawable(resources, id, null)

inline fun View.getCompatDimen(@DimenRes id: Int) =
  context.getCompatDimen(id)

inline fun Context.getCompatDimen(@DimenRes id: Int) =
  ResourcesCompat.getFloat(resources, id)

inline fun View.getCompatFont(@FontRes id: Int) =
  context.getCompatFont(id)

inline fun Context.getCompatFont(@FontRes id: Int) =
  ResourcesCompat.getFont(this, id)

inline fun parseColor(colorString: String) = Color.parseColor(colorString)
