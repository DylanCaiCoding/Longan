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

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

private var View.isAddedMarginTop: Boolean? by viewTags(R.id.tag_is_added_margin_top)
private var View.isAddedPaddingTop: Boolean? by viewTags(R.id.tag_is_added_padding_top)
private var View.isAddedMarginBottom: Boolean? by viewTags(R.id.tag_is_added_margin_bottom)

fun Fragment.immerseStatusBar(lightMode: Boolean = true) {
  activity?.immerseStatusBar(lightMode)
}

fun Activity.immerseStatusBar(lightMode: Boolean = true) {
  decorFitsSystemWindows = false
  window.decorView.windowInsetsControllerCompat?.systemBarsBehavior =
    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  transparentStatusBar()
  isLightStatusBar = lightMode
  contentView.addNavigationBarHeightToMarginBottom()
}

inline var Fragment.isLightStatusBar: Boolean
  get() = activity?.isLightStatusBar == true
  set(value) {
    view?.post { activity?.isLightStatusBar = value }
  }

inline var Activity.isLightStatusBar: Boolean
  get() = window.decorView.windowInsetsControllerCompat?.isAppearanceLightStatusBars == true
  set(value) {
    window.decorView.windowInsetsControllerCompat?.isAppearanceLightStatusBars = value
  }

inline var Fragment.statusBarColor: Int
  get() = activity?.statusBarColor ?: -1
  set(value) {
    activity?.statusBarColor = value
  }

@setparam:ColorInt
inline var Activity.statusBarColor: Int
  get() = window.statusBarColor
  set(value) {
    window.statusBarColor = value
  }

fun Fragment.transparentStatusBar() {
  activity?.transparentStatusBar()
}

fun Activity.transparentStatusBar() {
  statusBarColor = Color.TRANSPARENT
}

inline var Fragment.isStatusBarVisible: Boolean
  get() = activity?.isStatusBarVisible == true
  set(value) {
    activity?.isStatusBarVisible = value
  }

inline var Activity.isStatusBarVisible: Boolean
  get() = window.decorView.isStatusBarVisible
  set(value) {
    window.decorView.isStatusBarVisible = value
  }

inline var View.isStatusBarVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.statusBars()) == true
  set(value) {
    windowInsetsControllerCompat?.run {
      if (value) show(Type.statusBars()) else hide(Type.statusBars())
    }
  }

val statusBarHeight: Int
  get() = topActivity.window.decorView.rootWindowInsetsCompat?.getInsets(Type.statusBars())?.top
    ?: application.resources.getIdentifier("status_bar_height", "dimen", "android")
      .let { if (it > 0) application.resources.getDimensionPixelSize(it) else 0 }

fun View.addStatusBarHeightToMarginTop() = post {
  if (isStatusBarVisible && isAddedMarginTop != true) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
      updateMargins(top = topMargin + statusBarHeight)
      isAddedMarginTop = true
    }
  }
}

fun View.subtractStatusBarHeightToMarginTop() = post {
  if (isStatusBarVisible && isAddedMarginTop == true) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
      updateMargins(top = topMargin - statusBarHeight)
      isAddedMarginTop = false
    }
  }
}

fun View.addStatusBarHeightToPaddingTop() = post {
  if (isAddedPaddingTop != true) {
    updatePadding(top = paddingTop + statusBarHeight)
    updateLayoutParams {
      height = measuredHeight + statusBarHeight
    }
    isAddedPaddingTop = true
  }
}

fun View.subtractStatusBarHeightToPaddingTop() = post {
  if (isAddedPaddingTop == true) {
    updatePadding(top = paddingTop - statusBarHeight)
    updateLayoutParams {
      height = measuredHeight - statusBarHeight
    }
    isAddedPaddingTop = false
  }
}

inline var Fragment.isLightNavigationBar: Boolean
  get() = activity?.isLightNavigationBar == true
  set(value) {
    activity?.isLightNavigationBar = value
  }

inline var Activity.isLightNavigationBar: Boolean
  get() = window.decorView.windowInsetsControllerCompat?.isAppearanceLightNavigationBars == true
  set(value) {
    window.decorView.windowInsetsControllerCompat?.isAppearanceLightNavigationBars = value
  }

fun Fragment.transparentNavigationBar() {
  activity?.transparentNavigationBar()
}

fun Activity.transparentNavigationBar() {
  navigationBarColor = Color.TRANSPARENT
}

inline var Fragment.navigationBarColor: Int
  get() = activity?.navigationBarColor ?: -1
  set(value) {
    activity?.navigationBarColor = value
  }

inline var Activity.navigationBarColor: Int
  get() = window.navigationBarColor
  set(value) {
    window.navigationBarColor = value
  }

inline var Fragment.isNavigationBarVisible: Boolean
  get() = activity?.isNavigationBarVisible == true
  set(value) {
    activity?.isNavigationBarVisible = value
  }

inline var Activity.isNavigationBarVisible: Boolean
  get() = window.decorView.isNavigationBarVisible
  set(value) {
    window.decorView.isNavigationBarVisible = value
  }

inline var View.isNavigationBarVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.navigationBars()) == true
  set(value) {
    windowInsetsControllerCompat?.run {
      if (value) show(Type.navigationBars()) else hide(Type.navigationBars())
    }
  }

val navigationBarHeight: Int
  get() = topActivity.window.decorView.rootWindowInsetsCompat?.getInsets(Type.navigationBars())?.bottom
    ?: application.resources.getIdentifier("navigation_bar_height", "dimen", "android")
      .let { if (it > 0) application.resources.getDimensionPixelSize(it) else 0 }

fun View.addNavigationBarHeightToMarginBottom() = post {
  if (isNavigationBarVisible && isAddedMarginBottom != true) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
      updateMargins(bottom = bottomMargin + navigationBarHeight)
      isAddedMarginBottom = true
    }
  }
}

fun View.subtractNavigationBarHeightToMarginBottom() = post {
  if (isNavigationBarVisible && isAddedMarginBottom == true) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
      updateMargins(bottom = bottomMargin - navigationBarHeight)
      isAddedMarginBottom = false
    }
  }
}
