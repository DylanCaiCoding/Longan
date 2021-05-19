@file:Suppress("unused")

package com.dylanc.longan

import android.app.Activity
import android.view.Window
import androidx.core.view.WindowInsetsCompat.Type
import androidx.fragment.app.Fragment


/**
 * @author Dylan Cai
 */

inline val screenWith: Int get() = application.resources.displayMetrics.widthPixels

inline val screenHeight: Int get() = application.resources.displayMetrics.heightPixels

inline var Activity.isSystemBarVisible: Boolean
  get() = window.isSystemBarVisible
  set(value) {
    window.isSystemBarVisible = value
  }

inline var Fragment.isSystemBarVisible: Boolean
  get() = activity?.isSystemBarVisible == true
  set(value) {
    activity?.isSystemBarVisible = value
  }

inline var Window.isSystemBarVisible: Boolean
  get() = decorView.rootWindowInsetsCompat?.isVisible(Type.systemBars()) == true
  set(value) {
    insetControllerCompat?.run {
      if (value) show(Type.systemBars()) else hide(Type.systemBars())
    }
  }