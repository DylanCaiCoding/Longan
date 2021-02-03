@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * @author Dylan Cai
 */

fun Activity.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  window.decorView.snackbar(text, block)

fun Activity.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  window.decorView.snackbar(resId, block)

fun Fragment.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  requireView().snackbar(text, block)

fun Fragment.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  requireView().snackbar(resId, block)

fun View.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, text, Snackbar.LENGTH_SHORT).apply(block).apply { show() }

fun View.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, resId, Snackbar.LENGTH_SHORT).apply(block).apply { show() }

fun Activity.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  window.decorView.longSnackbar(text, block)

fun Activity.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  window.decorView.longSnackbar(resId, block)

fun Fragment.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  requireView().longSnackbar(text, block)

fun Fragment.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  requireView().longSnackbar(resId, block)

fun View.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, text, Snackbar.LENGTH_LONG).apply(block).apply { show() }

fun View.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, resId, Snackbar.LENGTH_LONG).apply(block).apply { show() }