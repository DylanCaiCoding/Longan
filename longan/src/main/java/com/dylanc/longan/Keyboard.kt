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

import android.view.View
import androidx.core.view.WindowInsetsCompat.Type

fun View.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

fun View.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

fun View.toggleKeyboard() = if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline var View.isKeyboardVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true
  set(value) {
    if (value) showKeyboard() else hideKeyboard()
  }

inline val View.keyboardHeight: Int
  get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1
