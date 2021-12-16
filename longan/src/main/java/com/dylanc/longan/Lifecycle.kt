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
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope


fun Application.doOnActivityLifecycle(
  onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
  onActivityStarted: ((Activity) -> Unit)? = null,
  onActivityResumed: ((Activity) -> Unit)? = null,
  onActivityPaused: ((Activity) -> Unit)? = null,
  onActivityStopped: ((Activity) -> Unit)? = null,
  onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
  onActivityDestroyed: ((Activity) -> Unit)? = null,
): Application.ActivityLifecycleCallbacks =
  object : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      onActivityCreated?.invoke(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
      onActivityStarted?.invoke(activity)
    }

    override fun onActivityResumed(activity: Activity) {
      onActivityResumed?.invoke(activity)
    }

    override fun onActivityPaused(activity: Activity) {
      onActivityPaused?.invoke(activity)
    }

    override fun onActivityStopped(activity: Activity) {
      onActivityStopped?.invoke(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
      onActivitySaveInstanceState?.invoke(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
      onActivityDestroyed?.invoke(activity)
    }
  }.also {
    registerActivityLifecycleCallbacks(it)
  }

fun Fragment.doOnViewLifecycle(
  onCreateView: (() -> Unit)? = null,
  onStart: (() -> Unit)? = null,
  onResume: (() -> Unit)? = null,
  onPause: (() -> Unit)? = null,
  onStop: (() -> Unit)? = null,
  onDestroyView: (() -> Unit)? = null,
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
  lifecycle.addObserver(object : DefaultLifecycleObserver {
    fun onCreate() = onCreate?.invoke()

    fun onStart() = onStart?.invoke()

    fun onResume() = onResume?.invoke()

    fun onPause() = onPause?.invoke()

    fun onStop() = onStop?.invoke()

    fun onDestroy() = onDestroy?.invoke()
  })

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope
