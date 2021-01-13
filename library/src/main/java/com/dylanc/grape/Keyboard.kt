@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.*
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

fun Activity.showKeyboard() = window.decorView.showKeyboard()

fun Fragment.showKeyboard() = requireView().showKeyboard()

fun View.showKeyboard() = windowInsetsControllerCompat?.show(Type.ime())

fun Activity.hideKeyboard() = window.decorView.hideKeyboard()

fun Fragment.hideKeyboard() = requireView().hideKeyboard()

fun View.hideKeyboard() = windowInsetsControllerCompat?.hide(Type.ime())

fun Activity.toggleKeyboard() = window.decorView.toggleKeyboard()

fun Fragment.toggleKeyboard() = requireView().toggleKeyboard()

fun View.toggleKeyboard() =
  if (isKeyboardVisible) hideKeyboard() else showKeyboard()

val Activity.isKeyboardVisible: Boolean
  get() = window.decorView.isKeyboardVisible

val Fragment.isKeyboardVisible: Boolean
  get() = requireView().isKeyboardVisible

val View.isKeyboardVisible: Boolean
  get() = windowInsetsCompat?.isVisible(Type.ime()) == true