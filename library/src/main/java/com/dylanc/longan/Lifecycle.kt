@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author Dylan Cai
 */

inline fun Fragment.doOnViewLifecycle(
  noinline onCreateView: (() -> Unit)? = null,
  noinline onStart: (() -> Unit)? = null,
  noinline onResume: (() -> Unit)? = null,
  noinline onPause: (() -> Unit)? = null,
  noinline onStop: (() -> Unit)? = null,
  noinline onDestroyView: (() -> Unit)? = null,
) =
  viewLifecycleOwner.doOnLifecycle(onCreateView, onStart, onResume, onPause, onStop, onDestroyView)

fun LifecycleOwner.doOnLifecycle(
  onCreate: (() -> Unit)? = null,
  onStart: (() -> Unit)? = null,
  onResume: (() -> Unit)? = null,
  onPause: (() -> Unit)? = null,
  onStop: (() -> Unit)? = null,
  onDestroy: (() -> Unit)? = null,
) =
  object : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
      onCreate?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
      onStart?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
      onResume?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
      onPause?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
      onStop?.invoke()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
      onDestroy?.invoke()
    }
  }.let {
    lifecycle.addObserver(it)
  }