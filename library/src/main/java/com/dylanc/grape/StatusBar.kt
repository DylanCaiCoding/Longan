@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager


inline var Activity.isStatusBarLightMode: Boolean
  get() = window.isStatusBarLightMode
  set(value) {
    window.isStatusBarLightMode = value
  }

inline var Window.isStatusBarLightMode: Boolean
  get() =
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
        insetsController?.systemBarsAppearance == WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
        @Suppress("DEPRECATION")
        attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == 0
      else ->
        false
    }
  set(value) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (value) {
        insetsController?.setSystemBarsAppearance(
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
      } else {
        insetsController?.setSystemBarsAppearance(
          0,
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
      }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      @Suppress("DEPRECATION")
      decorView.systemUiVisibility = if (value) {
        decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      } else {
        decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
      }
    }
  }
