@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment


inline fun Fragment.immerseStatusBar(lightMode: Boolean = true, addBottomMargin: Boolean = true) =
  activity?.immerseStatusBar(lightMode, addBottomMargin)

inline fun Activity.immerseStatusBar(lightMode: Boolean = true, addBottomMargin: Boolean = true) {
  decorFitsSystemWindows = false
  windowInsetsControllerCompat?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  transparentStatusBar()
  isLightStatusBar = lightMode
  if (addBottomMargin) contentView.addNavigationBarHeightToMarginBottom()
}

inline var Fragment.isLightStatusBar: Boolean
  get() = activity?.isLightStatusBar == true
  set(value) {
    view?.post { activity?.isLightStatusBar = value }
  }

inline var Activity.isLightStatusBar: Boolean
  get() = windowInsetsControllerCompat?.isAppearanceLightStatusBars == true
  set(value) {
    windowInsetsControllerCompat?.isAppearanceLightStatusBars = value
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

inline var Fragment.isStatusBarVisible: Boolean
  get() = activity?.isStatusBarVisible == true
  set(value) {
    activity?.isStatusBarVisible = value
  }

inline var Activity.isStatusBarVisible: Boolean
  get() = window.decorView.rootWindowInsetsCompat?.isVisible(Type.statusBars()) == true
  set(value) {
    windowInsetsControllerCompat?.run {
      if (value) show(Type.statusBars()) else hide(Type.statusBars())
    }
  }

val statusBarHeight: Int
  get() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      (application.getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics.windowInsets
        .getInsetsIgnoringVisibility(WindowInsets.Type.statusBars() or WindowInsets.Type.displayCutout()).top
    } else {
      application.resources.getIdentifier("status_bar_height", "dimen", "android").let {
        if (it > 0) {
          application.resources.getDimensionPixelSize(it)
        } else {
          Rect().apply { topActivity.window.decorView.getWindowVisibleDisplayFrame(this) }.top
        }
      }
    }

inline fun Fragment.transparentStatusBar() =
  activity?.immerseStatusBar()

inline fun Activity.transparentStatusBar() {
  statusBarColor = Color.TRANSPARENT
}

fun View.addStatusBarHeightToMarginTop() {
  if (isAddedMarginTop != true) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
      updateMargins(top = topMargin + statusBarHeight)
      isAddedMarginTop = true
    }
  }
}

fun View.subtractStatusBarHeightToMarginTop() {
  if (isAddedMarginTop == true) {
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
  get() = windowInsetsControllerCompat?.isAppearanceLightNavigationBars == true
  set(value) {
    windowInsetsControllerCompat?.isAppearanceLightNavigationBars = value
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
    windowInsetsControllerCompat?.run {
      if (value) show(Type.navigationBars()) else hide(Type.navigationBars())
    }
  }

inline val View.isNavigationBarVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.navigationBars()) == true

inline val navigationBarHeight: Int
  get() =
    application.resources.getIdentifier("navigation_bar_height", "dimen", "android")
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
