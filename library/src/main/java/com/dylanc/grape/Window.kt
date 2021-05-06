@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author Dylan Cai
 */

//inline val Window.insetsCompat: WindowInsetsCompat?
//  get() = WindowCompat.getInsets(this)

inline val View.rootWindowInsetsCompat: WindowInsetsCompat?
  get() = ViewCompat.getRootWindowInsets(this)

inline val Window.insetControllerCompat: WindowInsetsControllerCompat?
  get() = WindowCompat.getInsetsController(this, decorView)

inline val View.windowInsetsControllerCompat: WindowInsetsControllerCompat?
  get() = ViewCompat.getWindowInsetsController(this)

inline fun Window.setDecorFitsSystemWindowsCompat(decorFitsSystemWindows: Boolean) =
  WindowCompat.setDecorFitsSystemWindows(this, decorFitsSystemWindows)