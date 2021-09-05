@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * @author Dylan Cai
 */

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Activity.snackbar(@StringRes message: Int) =
  window.decorView.snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.snackbar(@StringRes message: Int) =
  requireView().snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.snackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Activity.longSnackbar(@StringRes message: Int) =
  window.decorView.longSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.longSnackbar(@StringRes message: Int) =
  requireView().longSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun View.longSnackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun Activity.indefiniteSnackbar(@StringRes message: Int) =
  window.decorView.indefiniteSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun Fragment.indefiniteSnackbar(@StringRes message: Int) =
  requireView().indefiniteSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun View.indefiniteSnackbar(@StringRes message: Int) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Activity.snackbar(message: CharSequence) =
  window.decorView.snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.snackbar(message: CharSequence) =
  requireView().snackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun View.snackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Activity.longSnackbar(message: CharSequence) =
  window.decorView.longSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longSnackbar(message: CharSequence) =
  requireView().longSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun View.longSnackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Activity.indefiniteSnackbar(message: CharSequence) =
  window.decorView.indefiniteSnackbar(message)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.indefiniteSnackbar(message: CharSequence) =
  requireView().indefiniteSnackbar(message)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun View.indefiniteSnackbar(message: CharSequence) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Activity.snackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  window.decorView.snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.snackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  requireView().snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text resource.
 */
inline fun View.snackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Activity.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  window.decorView.longSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  requireView().longSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text resource.
 */
inline fun View.longSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Activity.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  window.decorView.indefiniteSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  requireView().indefiniteSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text resource.
 */
inline fun View.indefiniteSnackbar(@StringRes message: Int, @StringRes actionText: Int, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Activity.snackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  window.decorView.snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.snackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  requireView().snackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
inline fun View.snackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Activity.longSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  window.decorView.longSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.longSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  requireView().longSnackbar(message, actionText, action)
/**
 * Display Snackbar with the [Snackbar.LENGTH_LONG] duration.
 *
 * @param message the message text.
 */
inline fun View.longSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    .setAction(actionText, action)
    .apply { show() }

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Activity.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  window.decorView.indefiniteSnackbar(message, actionText, action)

/**
 * Display the Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun Fragment.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  requireView().indefiniteSnackbar(message, actionText, action)

/**
 * Display Snackbar with the [Snackbar.LENGTH_INDEFINITE] duration.
 *
 * @param message the message text.
 */
inline fun View.indefiniteSnackbar(message: CharSequence, actionText: CharSequence, noinline action: (View) -> Unit) =
  Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    .setAction(actionText, action)
    .apply { show() }
