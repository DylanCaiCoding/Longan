@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

/**
 * @author Dylan Cai
 */

inline fun Fragment.doOnViewLifecycle(
  crossinline onCreateView: () -> Unit = {},
  crossinline onStart: () -> Unit = {},
  crossinline onResume: () -> Unit = {},
  crossinline onPause: () -> Unit = {},
  crossinline onStop: () -> Unit = {},
  crossinline onDestroyView: () -> Unit = {},
) =
  viewLifecycleOwner.doOnLifecycle(onCreateView, onStart, onResume, onPause, onStop, onDestroyView)

inline fun LifecycleOwner.doOnLifecycle(
  crossinline onCreate: () -> Unit = {},
  crossinline onStart: () -> Unit = {},
  crossinline onResume: () -> Unit = {},
  crossinline onPause: () -> Unit = {},
  crossinline onStop: () -> Unit = {},
  crossinline onDestroy: () -> Unit = {},
) =
  lifecycle.addObserver(object : DefaultLifecycleObserver {
    fun onCreate() = onCreate()
    fun onStart() = onStart()
    fun onResume() = onResume()
    fun onPause() = onPause()
    fun onStop() = onStop()
    fun onDestroy() = onDestroy()
  })

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope
