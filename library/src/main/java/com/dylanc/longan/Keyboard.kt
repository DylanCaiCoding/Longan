@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.widget.EditText
import androidx.core.view.WindowInsetsCompat.Type

/**
 * @author Dylan Cai
 */

inline fun EditText.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

inline fun EditText.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

inline fun EditText.toggleKeyboard() = if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val EditText.isKeyboardVisible: Boolean get() = rootWindowInsetsCompat?.isVisible(Type.ime()) == true

inline val EditText.keyboardHeight get() = rootWindowInsetsCompat?.getInsets(Type.ime())?.bottom ?: -1