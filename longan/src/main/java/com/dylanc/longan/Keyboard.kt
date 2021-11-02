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

import android.widget.EditText
import androidx.core.view.WindowInsetsCompat.Type


fun EditText.showKeyboard() =
  windowInsetsControllerCompat?.show(Type.ime())

fun EditText.hideKeyboard() =
  windowInsetsControllerCompat?.hide(Type.ime())

fun EditText.toggleKeyboard() =
  if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val EditText.isKeyboardVisible: Boolean
  get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true

inline val EditText.keyboardHeight: Int
  get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1
