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
import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
    windowInsetsControllerCompat?.run {
      val systemBars = WindowInsetsCompat.Type.systemBars()
      if (value) show(systemBars) else hide(systemBars)
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
