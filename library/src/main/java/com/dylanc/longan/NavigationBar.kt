@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.fragment.app.Fragment


private var View.isAddedMarginBottom: Boolean? by viewTags(-1003)

inline var Activity.isLightNavigationBar: Boolean
  get() = window.isLightNavigationBar
  set(value) {
    window.isLightNavigationBar = value
  }

inline var Fragment.isLightNavigationBar: Boolean
  get() = activity?.isLightNavigationBar == true
  set(value) {
    activity?.isLightNavigationBar = value
  }

inline var Window.isLightNavigationBar: Boolean
  get() = insetControllerCompat?.isAppearanceLightNavigationBars == true
  set(value) {
    insetControllerCompat?.isAppearanceLightNavigationBars = value
  }

inline var Activity.navigationBarColor: Int
  get() = window.navigationBarColor
  set(value) {
    window.navigationBarColor = value
  }

inline var Fragment.navigationBarColor: Int
  get() = activity?.navigationBarColor ?: -1
  set(value) {
    activity?.navigationBarColor = value
  }

inline var Activity.isNavigationBarVisible: Boolean
  get() = window.isNavigationBarVisible
  set(value) {
    window.isNavigationBarVisible = value
  }

inline var Fragment.isNavigationBarVisible: Boolean
  get() = activity?.isNavigationBarVisible == true
  set(value) {
    activity?.isNavigationBarVisible = value
  }

inline var Window.isNavigationBarVisible: Boolean
  get() = decorView.isNavigationBarVisible
  set(value) {
    insetControllerCompat?.run {
      if (value) show(Type.navigationBars()) else hide(Type.navigationBars())
    }
  }

inline val View.isNavigationBarVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.navigationBars()) == true

inline val navigationBarHeight: Int
  get() =
    application.resources.getIdentifier("navigation_bar_height", "dimen", "android").let {
      if (it > 0) application.resources.getDimensionPixelSize(it) else 0
    }

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