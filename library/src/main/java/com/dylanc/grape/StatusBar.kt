@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager


var Activity.isStatusBarLightMode: Boolean
  get() =
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
        window.insetsController?.systemBarsAppearance == WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
        @Suppress("DEPRECATION")
        window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == 0
      else ->
        false
    }
  set(value) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      if (value) {
        window.insetsController?.setSystemBarsAppearance(
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
      } else {
        window.insetsController?.setSystemBarsAppearance(
          0,
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
      }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      @Suppress("DEPRECATION")
      window.decorView.systemUiVisibility = if (value) {
        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
      } else {
        window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
      }
    }
  }
