@file:Suppress("unused")

package com.dylanc.grape

import android.widget.Toast
import androidx.annotation.StringRes
import me.drakeet.support.toast.ToastCompat

/**
 * @author Dylan Cai
 */

inline fun toast(text: String?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(application, text, duration).apply(block).apply { show() }

inline fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(application, resId, duration).apply(block).apply { show() }

inline fun longToast(text: String?, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(application, text, Toast.LENGTH_LONG).apply(block).apply { show() }

inline fun longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(application, resId, Toast.LENGTH_LONG).apply(block).apply { show() }