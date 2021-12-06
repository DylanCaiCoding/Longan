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

import android.content.res.TypedArray
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun List<View>.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  setOnClickListener {
    val view = if (isSharingIntervals) context.activity?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    val lastTime = view.lastClickTime ?: 0L
    if (currentTime - lastTime > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnClick(crossinline block: () -> Unit) =
  forEach { it.doOnClick(block) }

inline fun View.doOnClick(crossinline block: () -> Unit) =
  setOnClickListener { block() }

fun List<View>.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnLongClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnLongClick {
    val view = if (isSharingIntervals) context.activity?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    val lastTime = view.lastClickTime ?: 0L
    if (currentTime - lastTime > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnLongClick(crossinline block: () -> Unit) =
  forEach { it.doOnLongClick(block) }

inline fun View.doOnLongClick(crossinline block: () -> Unit) =
  setOnLongClickListener {
    block()
    true
  }

var View.roundCorners: Float
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
      override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(0, 0, view.width, view.height, value)
      }
    }
  }

fun View?.isTouchedAt(x: Float, y: Float): Boolean =
  isTouchedAt(x.toInt(), y.toInt())

fun View?.isTouchedAt(x: Int, y: Int): Boolean =
  this?.locationOnScreen?.run { x in left..right && y in top..bottom } ?: false

fun View.findTouchedChild(x: Float, y: Float): View? =
  findTouchedChild(x.toInt(), y.toInt())

fun View.findTouchedChild(x: Int, y: Int): View? =
  touchables.find { it.isTouchedAt(x, y) }

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

inline val View.rootWindowInsetsCompat: WindowInsetsCompat?
  get() = ViewCompat.getRootWindowInsets(this)

inline val View.windowInsetsControllerCompat: WindowInsetsControllerCompat?
  get() = ViewCompat.getWindowInsetsController(this)

fun View.doOnApplyWindowInsets(action: (View, WindowInsetsCompat) -> WindowInsetsCompat) =
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
