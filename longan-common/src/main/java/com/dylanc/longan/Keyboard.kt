@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type

/**
 * @author Dylan Cai
 */

inline fun EditText.showKeyboard() =
  ViewCompat.getWindowInsetsController(this)?.show(Type.ime())

inline fun EditText.hideKeyboard() =
  ViewCompat.getWindowInsetsController(this)?.hide(Type.ime())

inline fun EditText.toggleKeyboard() =
  if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val EditText.isKeyboardVisible: Boolean
  get() = ViewCompat.getRootWindowInsets(this)?.isVisible(Type.ime()) == true

inline val EditText.keyboardHeight
  get() = ViewCompat.getRootWindowInsets(this)?.getInsets(Type.ime())?.bottom ?: -1
