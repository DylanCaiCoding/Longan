@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author Dylan Cai
 */

inline val Window.insetControllerCompat: WindowInsetsControllerCompat?
  get() = WindowCompat.getInsetsController(this, decorView)

inline fun Window.setDecorFitsSystemWindowsCompat(decorFitsSystemWindows: Boolean) =
  WindowCompat.setDecorFitsSystemWindows(this, decorFitsSystemWindows)

inline val Window.contentView: View? get() = (decorView as ViewGroup).getChildAt(0)

inline val View.rootWindowInsetsCompat: WindowInsetsCompat?
  get() = ViewCompat.getRootWindowInsets(this)

inline val View.windowInsetsControllerCompat: WindowInsetsControllerCompat?
  get() = ViewCompat.getWindowInsetsController(this)

inline fun View.doOnApplyWindowInsets(noinline action: (View, WindowInsetsCompat) -> WindowInsetsCompat) =
  ViewCompat.setOnApplyWindowInsetsListener(this, action)