@file:Suppress("unused")

package com.dylanc.longan

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment


/**
 * @author Dylan Cai
 */

inline val screenWidth: Int get() = application.resources.displayMetrics.widthPixels

inline val screenHeight: Int get() = application.resources.displayMetrics.heightPixels

inline var Fragment.isFullScreen: Boolean
  get() = activity?.isFullScreen == true
  set(value) {
    activity?.isFullScreen = value
  }

inline var Activity.isFullScreen: Boolean
  get() = window.decorView.rootWindowInsetsCompat?.isVisible(WindowInsetsCompat.Type.systemBars()) == true
  set(value) {
    WindowCompat.getInsetsController(window, window.decorView)?.run {
      if (value) show(WindowInsetsCompat.Type.systemBars()) else hide(WindowInsetsCompat.Type.systemBars())
    }
  }

inline var Fragment.isLandscape: Boolean
  get() = activity?.isLandscape == true
  set(value) {
    activity?.isLandscape = value
  }

inline var Activity.isLandscape: Boolean
  get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
  set(value) {
    requestedOrientation = if (value) {
      ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    } else {
      ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
  }

inline var Fragment.isPortrait: Boolean
  get() = activity?.isPortrait == true
  set(value) {
    activity?.isPortrait = value
  }

inline var Activity.isPortrait: Boolean
  get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
  set(value) {
    requestedOrientation = if (value) {
      ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
      ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
  }
