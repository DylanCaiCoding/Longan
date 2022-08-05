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
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private var View.lastClickTime: Long? by viewTags(R.id.tag_last_click_time)
var debouncingClickIntervals = 500

@Deprecated("Use doOnDebouncingClick instead", ReplaceWith("doOnDebouncingClick(clickIntervals, isSharingIntervals, block)"))
fun List<View>.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingClick(clickIntervals, isSharingIntervals, block)

@Deprecated("Use doOnDebouncingClick instead", ReplaceWith("doOnDebouncingClick(clickIntervals, isSharingIntervals, block)"))
fun View.doOnClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingClick(clickIntervals, isSharingIntervals, block)

fun List<View>.doOnDebouncingClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnDebouncingClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnDebouncingClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  setOnClickListener {
    val view = if (isSharingIntervals) context.asActivity()?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    if (currentTime - (view.lastClickTime ?: 0L) > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnClick(crossinline block: () -> Unit) = forEach { it.doOnClick(block) }

inline fun View.doOnClick(crossinline block: () -> Unit) = setOnClickListener { block() }

@Deprecated("Use doOnDebouncingLongClick instead", ReplaceWith("doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)"))
fun List<View>.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)

@Deprecated("Use doOnDebouncingLongClick instead", ReplaceWith("doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)"))
fun View.doOnLongClick(clickIntervals: Int, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block)

fun List<View>.doOnDebouncingLongClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  forEach { it.doOnDebouncingLongClick(clickIntervals, isSharingIntervals, block) }

fun View.doOnDebouncingLongClick(clickIntervals: Int = debouncingClickIntervals, isSharingIntervals: Boolean = false, block: () -> Unit) =
  doOnLongClick {
    val view = if (isSharingIntervals) context.asActivity()?.window?.decorView ?: this else this
    val currentTime = System.currentTimeMillis()
    if (currentTime - (view.lastClickTime ?: 0L) > clickIntervals) {
      view.lastClickTime = currentTime
      block()
    }
  }

inline fun List<View>.doOnLongClick(crossinline block: () -> Unit) = forEach { it.doOnLongClick(block) }

inline fun View.doOnLongClick(crossinline block: () -> Unit) =
  setOnLongClickListener {
    block()
    true
  }

fun View.expandClickArea(expandSize: Float) = expandClickArea(expandSize.toInt())

fun View.expandClickArea(expandSize: Int) =
  expandClickArea(expandSize, expandSize, expandSize, expandSize)

fun View.expandClickArea(top: Float, left: Float, right: Float, bottom: Float) =
  expandClickArea(top.toInt(), left.toInt(), right.toInt(), bottom.toInt())

fun View.expandClickArea(top: Int, left: Int, right: Int, bottom: Int) {
  val parent = parent as? ViewGroup ?: return
  parent.post {
    val rect = Rect()
    getHitRect(rect)
    rect.top -= top
    rect.left -= left
    rect.right += right
    rect.bottom += bottom
    val touchDelegate = parent.touchDelegate
    if (touchDelegate == null || touchDelegate !is MultiTouchDelegate) {
      parent.touchDelegate = MultiTouchDelegate(rect, this)
    } else {
      touchDelegate.put(rect, this)
    }
  }
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
  this?.locationOnScreen?.contains(x, y) == true

fun View.findTouchedChild(x: Float, y: Float): View? =
  findTouchedChild(x.toInt(), y.toInt())

fun View.findTouchedChild(x: Int, y: Int): View? =
  touchables.find { it.isTouchedAt(x, y) }

/**
 * Computes the coordinates of this view on the screen.
 */
inline val View.locationOnScreen: Rect
  get() = IntArray(2).let {
    getLocationOnScreen(it)
    Rect(it[0], it[1], it[0] + width, it[1] + height)
  }

@Deprecated(
  "Replace with new api",
  replaceWith = ReplaceWith("withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)")
)
inline fun View.withStyledAttrs(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) =
  context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)

inline fun View.withStyledAttributes(
  set: AttributeSet?,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0,
  block: TypedArray.() -> Unit
) =
  context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)

val View.rootWindowInsetsCompat: WindowInsetsCompat? by viewTags(R.id.tag_root_window_insets) {
  ViewCompat.getRootWindowInsets(this)
}

val View.windowInsetsControllerCompat: WindowInsetsControllerCompat? by viewTags(R.id.tag_window_insets_controller) {
  ViewCompat.getWindowInsetsController(this)
}

fun View.doOnApplyWindowInsets(action: (View, WindowInsetsCompat) -> WindowInsetsCompat) =
  ViewCompat.setOnApplyWindowInsetsListener(this, action)

fun <T> viewTags(key: Int) = object : ReadWriteProperty<View, T?> {
  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: View, property: KProperty<*>) =
    thisRef.getTag(key) as? T

  override fun setValue(thisRef: View, property: KProperty<*>, value: T?) =
    thisRef.setTag(key, value)
}

@Suppress("UNCHECKED_CAST")
fun <T> viewTags(key: Int, block: View.() -> T) = ReadOnlyProperty<View, T> { thisRef, _ ->
  if (thisRef.getTag(key) == null) {
    thisRef.setTag(key, block(thisRef))
  }
  thisRef.getTag(key) as T
}

class MultiTouchDelegate(bound: Rect, delegateView: View) : TouchDelegate(bound, delegateView) {
  private val map = mutableMapOf<View, Pair<Rect, TouchDelegate>>()
  private var targetDelegate: TouchDelegate? = null

  init {
    put(bound, delegateView)
  }

  fun put(bound: Rect, delegateView: View) {
    map[delegateView] = bound to TouchDelegate(bound, delegateView)
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    val x = event.x.toInt()
    val y = event.y.toInt()
    when (event.actionMasked) {
      MotionEvent.ACTION_DOWN -> {
        targetDelegate = map.entries.find { it.value.first.contains(x, y) }?.value?.second
      }
      MotionEvent.ACTION_CANCEL -> {
        targetDelegate = null
      }
    }
    return targetDelegate?.onTouchEvent(event) ?: false
  }
}
