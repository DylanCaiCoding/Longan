package com.dylanc.grape

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author Dylan Cai
 */

val View.windowInsetsCompat: WindowInsetsCompat?
  get() = ViewCompat.getRootWindowInsets(this)

val View.windowInsetsControllerCompat: WindowInsetsControllerCompat?
  get() = ViewCompat.getWindowInsetsController(this)