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
