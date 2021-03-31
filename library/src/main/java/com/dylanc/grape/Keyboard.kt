@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.view.View
import androidx.core.view.WindowInsetsCompat.Type
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

inline fun Activity.showKeyboard() = window.decorView.showKeyboard()

inline fun Fragment.showKeyboard() = requireView().showKeyboard()

inline fun View.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

inline fun Activity.hideKeyboard() = window.decorView.hideKeyboard()

inline fun Fragment.hideKeyboard() = requireView().hideKeyboard()

inline fun View.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

inline fun Activity.toggleKeyboard() = window.decorView.toggleKeyboard()

inline fun Fragment.toggleKeyboard() = requireView().toggleKeyboard()

inline fun View.toggleKeyboard() = if (isKeyboardVisible) hideKeyboard() else showKeyboard()

inline val Activity.isKeyboardVisible: Boolean get() = window.decorView.isKeyboardVisible

inline val Fragment.isKeyboardVisible: Boolean get() = requireView().isKeyboardVisible

inline val View.isKeyboardVisible: Boolean get() = windowInsetsCompat?.isVisible(Type.ime()) == true