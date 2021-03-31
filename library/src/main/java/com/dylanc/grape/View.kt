@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes

/**
 * @author Dylan Cai
 */

inline fun View.withStyledAttrs(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) {
  context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes).apply(block).recycle()
}

inline fun View?.isTouchedAt(x: Float, y: Float) = isTouchedAt(x.toInt(), y.toInt())

inline fun View?.isTouchedAt(x: Int, y: Int): Boolean {
  if (this == null) return false
  var isTouchedAt = false
  locationOnScreen {
    isTouchedAt = x in left..right && y in top..bottom
  }
  return isTouchedAt
}

inline fun View.locationOnScreen(crossinline block: LocationOnScreen.() -> Unit) {
  val locationOnScreen = IntArray(2)
    .apply { getLocationOnScreen(this) }
  val left = locationOnScreen[0]
  val top = locationOnScreen[1]
  val right = left + width
  val bottom = top + height
  block(LocationOnScreen(left, top, right, bottom))
}

inline fun View.findTouchedChild(view: View, x: Int, y: Int): View? {
  var targetView: View? = null
  val touchableViews = view.touchables
  for (child in touchableViews) {
    if (child.isTouchedAt(x, y)) {
      targetView = child
      break
    }
  }
  return targetView
}

data class LocationOnScreen(
  val left: Int,
  val top: Int,
  val right: Int,
  val bottom: Int
)