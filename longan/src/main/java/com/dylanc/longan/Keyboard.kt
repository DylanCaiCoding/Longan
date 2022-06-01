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

fun Activity.showKeyboard() = window.decorView.showKeyboard()

fun Fragment.showKeyboard() = view?.showKeyboard()

fun View.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

fun Activity.hideKeyboard() = window.decorView.hideKeyboard()

fun Fragment.hideKeyboard() = view?.hideKeyboard()

fun View.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

fun Activity.toggleKeyboard() = window.decorView.toggleKeyboard()

fun Fragment.toggleKeyboard() = view?.toggleKeyboard()

fun View.toggleKeyboard() = if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline var Activity.isKeyboardVisible: Boolean
  get() = window.decorView.isKeyboardVisible
  set(value) {
    window.decorView.isKeyboardVisible = value
  }

inline var Fragment.isKeyboardVisible: Boolean
  get() = view?.isKeyboardVisible == true
  set(value) {
    view?.isKeyboardVisible = value
  }

inline var View.isKeyboardVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true
  set(value) {
    if (value) showKeyboard() else hideKeyboard()
  }

inline val Activity.keyboardHeight: Int
  get() = window.decorView.keyboardHeight

inline val Fragment.keyboardHeight: Int
  get() = view?.keyboardHeight ?: -1

inline val View.keyboardHeight: Int
  get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1
