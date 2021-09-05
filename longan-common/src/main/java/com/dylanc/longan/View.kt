@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.res.TypedArray
import android.location.Location
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
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

inline fun List<View>.doOnClick(crossinline block: () -> Unit) =
  forEach { it.doOnClick(block) }

inline fun View.doOnClick(crossinline block: () -> Unit) =
  setOnClickListener { block() }

inline fun List<View>.doOnLongClick(crossinline block: () -> Unit) =
  forEach { it.doOnLongClick(block) }

inline fun View.doOnLongClick(crossinline block: () -> Unit) =
  setOnLongClickListener {
    block()
    true
  }

inline fun View?.isTouchedAt(x: Float, y: Float) = isTouchedAt(x.toInt(), y.toInt())

inline fun View?.isTouchedAt(x: Int, y: Int) =
  this?.locationOnScreen?.run { x in left..right && y in top..bottom } ?: false

inline fun ViewGroup.findTouchedChild(view: View, x: Int, y: Int) =
  view.touchables.find { it.isTouchedAt(x, y) }

/**
 * Computes the coordinates of this view on the screen.
 */
inline val View.locationOnScreen: LocationOnScreen
  get() = IntArray(2).let {
    getLocationOnScreen(it)
    LocationOnScreen(it[0], it[1], it[0] + width, it[1] + height)
  }

inline fun View.withStyledAttrs(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) {
  context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes).apply(block).recycle()
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

data class LocationOnScreen(
  val left: Int,
  val top: Int,
  val right: Int,
  val bottom: Int,
)
