@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment


inline var Activity.isLightStatusBar: Boolean
  get() = window.isLightStatusBar
  set(value) {
    window.isLightStatusBar = value
  }

inline var Fragment.isLightStatusBar: Boolean
  get() = activity?.isLightStatusBar == true
  set(value) {
    activity?.isLightStatusBar = value
  }

inline var Window.isLightStatusBar: Boolean
  get() = insetControllerCompat?.isAppearanceLightStatusBars == true
  set(value) {
    insetControllerCompat?.isAppearanceLightStatusBars = value
  }

@setparam:ColorInt
inline var Activity.statusBarColor: Int
  get() = window.statusBarColor
  set(value) {
    window.statusBarColor = value
  }

inline var Fragment.statusBarColor: Int
  get() = activity?.statusBarColor ?: -1
  set(value) {
    activity?.statusBarColor = value
  }

inline var Activity.isStatusBarVisible: Boolean
  get() = window.isStatusBarVisible
  set(value) {
    window.isStatusBarVisible = value
  }

inline var Fragment.isStatusBarVisible: Boolean
  get() = activity?.isStatusBarVisible == true
  set(value) {
    activity?.isStatusBarVisible = value
  }

inline var Window.isStatusBarVisible: Boolean
  get() = decorView.rootWindowInsetsCompat?.isVisible(Type.statusBars()) == true
  set(value) {
    insetControllerCompat?.run {
      if (value) show(Type.statusBars()) else hide(Type.statusBars())
    }
  }

inline val statusBarHeight: Int
  get() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
      windowManager.currentWindowMetrics.windowInsets
        .getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()).top
    else
      application.resources.getIdentifier("status_bar_height", "dimen", "android").let {
        if (it > 0) application.resources.getDimensionPixelSize(it) else topActivity.window.statusBarHeight
      }

inline val Window.statusBarHeight: Int
  get() = Rect().apply { decorView.getWindowVisibleDisplayFrame(this) }.top

inline fun Activity.immerseStatusBar(lightMode: Boolean = true) =
  window.immerseStatusBar(lightMode)

inline fun Fragment.immerseStatusBar(lightMode: Boolean = true) =
  activity?.immerseStatusBar(lightMode)

inline fun Window.immerseStatusBar(lightMode: Boolean = true) {
  setDecorFitsSystemWindowsCompat(false)
  insetControllerCompat?.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
  statusBarColor = Color.TRANSPARENT
  isLightStatusBar = lightMode
}

inline fun View.addStatusBarHeightToMarginTop() {
  updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(top = topMargin + statusBarHeight)
  }
}

inline fun View.subtractStatusBarHeightToMarginTop() {
  updateLayoutParams<ViewGroup.MarginLayoutParams> {
    updateMargins(top = topMargin - statusBarHeight)
  }
}

inline fun View.addStatusBarHeightToPaddingTop() = post {
  updatePadding(top = paddingTop + statusBarHeight)
  updateLayoutParams {
    height = measuredHeight + statusBarHeight
  }
}

inline fun View.subtractStatusBarHeightToPaddingTop() = post {
  updatePadding(top = paddingTop - statusBarHeight)
  updateLayoutParams {
    height = measuredHeight - statusBarHeight
  }
}