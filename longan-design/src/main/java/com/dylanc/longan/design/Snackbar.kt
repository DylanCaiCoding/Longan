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

package com.dylanc.longan.design

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
fun Activity.snackbar(@StringRes message: Int) =
  window.decorView.snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
fun Fragment.snackbar(@StringRes message: Int) =
  requireView().snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
fun View.snackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
fun Activity.longSnackbar(@StringRes message: Int) =
  window.decorView.longSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
fun Fragment.longSnackbar(@StringRes message: Int) =
  requireView().longSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
fun View.longSnackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
fun Activity.indefiniteSnackbar(@StringRes message: Int) =
  window.decorView.indefiniteSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
fun Fragment.indefiniteSnackbar(@StringRes message: Int) =
  requireView().indefiniteSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
fun View.indefiniteSnackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Activity.snackbar(message: CharSequence) =
  window.decorView.snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Fragment.snackbar(message: CharSequence) =
  requireView().snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun View.snackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Activity.longSnackbar(message: CharSequence) =
  window.decorView.longSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Fragment.longSnackbar(message: CharSequence) =
  requireView().longSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun View.longSnackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Activity.indefiniteSnackbar(message: CharSequence) =
  window.decorView.indefiniteSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Fragment.indefiniteSnackbar(message: CharSequence) =
  requireView().indefiniteSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun View.indefiniteSnackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Activity.snackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  window.decorView.snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Fragment.snackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  requireView().snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
fun View.snackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Activity.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  window.decorView.longSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Fragment.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  requireView().longSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
fun View.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Activity.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  window.decorView.indefiniteSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Fragment.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  requireView().indefiniteSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
fun View.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Activity.snackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  window.decorView.snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Fragment.snackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  requireView().snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun View.snackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Activity.longSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  window.decorView.longSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun Fragment.longSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  requireView().longSnackbar(message, actionText, action)
/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
fun View.longSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Activity.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  window.decorView.indefiniteSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun Fragment.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  requireView().indefiniteSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
fun View.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
