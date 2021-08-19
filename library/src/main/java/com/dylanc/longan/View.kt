@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Dylan Cai
 */

inline fun List<View>.setOnClickListener(noinline onClick: (View) -> Unit) =
  forEach { it.setOnClickListener(onClick) }

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

inline fun View?.isTouchedAt(x: Int, y: Int) =
  this?.locationOnScreen?.let { location ->
    x in location[0]..location[2] && y in location[1]..location[3]
  } ?: false

inline fun View.findTouchedChild(view: View, x: Int, y: Int) =
  view.touchables.find { it.isTouchedAt(x, y) }

/**
 * Computes the coordinates of this view on the screen, for example:
 * ```
 * val location = locationOnScreen
 * val left = location[0]
 * val top = location[1]
 * val right = location[2]
 * val bottom = location[3]
 * ```
 *
 * @return an array of four integers in which to hold the coordinates
 */
inline val View.locationOnScreen
  get() = IntArray(4).apply {
    val outLocation = IntArray(2)
    getLocationOnScreen(outLocation)
    this[0] = outLocation[0]          // left
    this[1] = outLocation[1]          // top
    this[2] = outLocation[0] + width  // right
    this[3] = outLocation[1] + height // bottom
  }

inline fun View.doOnApplyWindowInsets(noinline action: (View, WindowInsetsCompat) -> WindowInsetsCompat) =
  ViewCompat.setOnApplyWindowInsetsListener(this, action)

fun <T> viewTags(key: Int) = object : ReadWriteProperty<View, T?> {
  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: View, property: KProperty<*>) =
    thisRef.getTag(key) as T?

  override fun setValue(thisRef: View, property: KProperty<*>, value: T?) =
    thisRef.setTag(key, value)
}