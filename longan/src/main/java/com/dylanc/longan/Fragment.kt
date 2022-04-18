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

import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

fun <T : Fragment> T.withArguments(vararg pairs: Pair<String, *>) = apply {
  arguments = bundleOf(*pairs)
}

inline fun <reified T> Fragment.arguments(key: String) = lazy<T?> {
  arguments[key]
}

inline fun <reified T> Fragment.arguments(key: String, default: T) = lazy {
  arguments[key] ?: default
}

inline fun <reified T> Fragment.safeArguments(name: String) = lazy<T> {
  checkNotNull(arguments[name]) { "No intent value for key \"$name\"" }
}

fun Fragment.pressBackTwiceToExitApp(toastText: String, delayMillis: Long = 2000) =
  requireActivity().pressBackTwiceToExitApp(toastText, delayMillis, viewLifecycleOwner)

fun Fragment.pressBackTwiceToExitApp(@StringRes toastText: Int, delayMillis: Long = 2000) =
  requireActivity().pressBackTwiceToExitApp(toastText, delayMillis, viewLifecycleOwner)

fun Fragment.pressBackTwiceToExitApp(delayMillis: Long = 2000, onFirstBackPressed: () -> Unit) =
  requireActivity().pressBackTwiceToExitApp(delayMillis, viewLifecycleOwner, onFirstBackPressed)

fun Fragment.pressBackToNotExitApp() =
  requireActivity().pressBackToNotExitApp(viewLifecycleOwner)