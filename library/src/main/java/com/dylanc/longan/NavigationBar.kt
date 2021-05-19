@file:Suppress("unused")

package com.dylanc.longan

import android.app.Activity
import android.view.Window
import androidx.core.view.WindowInsetsCompat.Type
import androidx.fragment.app.Fragment


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
  get() = decorView.rootWindowInsetsCompat?.isVisible(Type.navigationBars()) == true
  set(value) {
    insetControllerCompat?.run {
      if (value) show(Type.navigationBars()) else hide(Type.navigationBars())
    }
  }