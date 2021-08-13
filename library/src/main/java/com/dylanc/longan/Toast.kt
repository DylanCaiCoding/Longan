@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes


/**
 * @author Dylan Cai
 */

inline fun toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  topActivity.toast(text, duration, block)

inline fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  topActivity.toast(resId, duration, block)

inline fun longToast(text: CharSequence?, block: Toast.() -> Unit = {}): Toast =
  topActivity.longToast(text, block)

inline fun longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  topActivity.longToast(resId, block)

inline fun Context.toast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  Toast.makeText(context, text, duration).apply(block).apply { showSafely() }

inline fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT, block: Toast.() -> Unit = {}): Toast =
  Toast.makeText(context, getString(resId), duration).apply(block).apply { showSafely() }

inline fun Context.longToast(text: CharSequence?, block: Toast.() -> Unit = {}): Toast =
  Toast.makeText(context, text, Toast.LENGTH_LONG).apply(block).apply { showSafely() }

inline fun Context.longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}): Toast =
  Toast.makeText(context, getString(resId), Toast.LENGTH_LONG).apply(block).apply { showSafely() }

fun Toast.showSafely() {
  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
    try {
      val tnField = Toast::class.java.getDeclaredField("mTN")
      tnField.isAccessible = true
      val tn = tnField.get(this)

      val handlerField = tnField.type.getDeclaredField("mHandler")
      handlerField.isAccessible = true
      val handler = handlerField.get(tn) as Handler

      val looper = checkNotNull(Looper.myLooper()) {
        "Can't toast on a thread that has not called Looper.prepare()"
      }
      handlerField.set(tn, object : Handler(looper) {
        override fun handleMessage(msg: Message) {
          try {
            handler.handleMessage(msg)
          } catch (ignored: WindowManager.BadTokenException) {
          }
        }
      })
    } catch (ignored: IllegalAccessException) {
    } catch (ignored: NoSuchFieldException) {
    }
  }
  show()
}