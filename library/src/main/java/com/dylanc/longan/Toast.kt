@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import me.drakeet.support.toast.ToastCompat

/**
 * @author Dylan Cai
 */

inline fun toast(text: Any?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  topActivity.toast(text.toString(), duration, block)

inline fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  topActivity.toast(resId, duration, block)

inline fun longToast(text: Any?, block: Toast.() -> Unit = {}): Toast =
  topActivity.longToast(text.toString(), block)

inline fun longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  topActivity.longToast(resId, block)

inline fun Context.toast(text: Any?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(context, text.toString(), duration).apply(block).apply { show() }

inline fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(context, getString(resId), duration).apply(block).apply { show() }

inline fun Context.longToast(text: Any?, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(context, text.toString(), Toast.LENGTH_LONG).apply(block).apply { show() }

inline fun Context.longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  ToastCompat.makeText(context, getString(resId), Toast.LENGTH_LONG).apply(block).apply { show() }