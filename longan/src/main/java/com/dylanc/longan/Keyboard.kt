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
import android.view.View
import androidx.core.view.WindowInsetsCompat.Type
import androidx.fragment.app.Fragment


fun Fragment.showKeyboard() = requireActivity().showKeyboard()

fun Activity.showKeyboard() = window.decorView.showKeyboard()

fun View.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

fun Fragment.hideKeyboard() = requireActivity().hideKeyboard()

fun Activity.hideKeyboard() = window.decorView.hideKeyboard()

fun View.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

fun Fragment.toggleKeyboard() = requireActivity().toggleKeyboard()

fun Activity.toggleKeyboard() = window.decorView.toggleKeyboard()

fun View.toggleKeyboard() = if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val Fragment.isKeyboardVisible: Boolean
  get() = requireActivity().isKeyboardVisible

inline val Activity.isKeyboardVisible: Boolean
  get() = window.decorView.isKeyboardVisible

inline val View.isKeyboardVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true

inline val Fragment.keyboardHeight: Int
  get() = requireActivity().keyboardHeight

inline val Activity.keyboardHeight: Int
  get() = window.decorView.keyboardHeight

inline val View.keyboardHeight: Int
  get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1
