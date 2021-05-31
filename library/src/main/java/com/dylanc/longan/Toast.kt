@file:Suppress("unused")

package com.dylanc.longan

import android.widget.Toast
import androidx.annotation.StringRes
import me.drakeet.support.toast.ToastCompat

/**
 * @author Dylan Cai
 */

inline fun toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(topActivity, text, duration).apply(block).apply { show() }

inline fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(topActivity, stringOf(resId), duration).apply(block).apply { show() }

inline fun longToast(text: CharSequence?, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(topActivity, text, Toast.LENGTH_LONG).apply(block).apply { show() }

inline fun longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(topActivity, stringOf(resId), Toast.LENGTH_LONG).apply(block).apply { show() }