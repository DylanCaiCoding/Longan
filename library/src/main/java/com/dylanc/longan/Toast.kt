@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


/**
 * @author Dylan Cai
 */

inline fun Fragment.toast(message: CharSequence?): Toast =
  requireActivity().toast(message)

inline fun Fragment.toast(@StringRes message: Int): Toast =
  requireActivity().toast(message)

inline fun Context.toast(message: CharSequence?): Toast =
  Toast.makeText(this, message, Toast.LENGTH_SHORT).fixBadTokenException().apply { show() }

inline fun Context.toast(@StringRes message: Int): Toast =
  Toast.makeText(this, message, Toast.LENGTH_SHORT).fixBadTokenException().apply { show() }

inline fun Fragment.longToast(message: CharSequence?): Toast =
  requireActivity().longToast(message)

inline fun Fragment.longToast(@StringRes message: Int): Toast =
  requireActivity().longToast(message)

inline fun Context.longToast(message: CharSequence?): Toast =
  Toast.makeText(this, message, Toast.LENGTH_LONG).fixBadTokenException().apply { show() }

inline fun Context.longToast(@StringRes message: Int): Toast =
  Toast.makeText(this, message, Toast.LENGTH_LONG).fixBadTokenException().apply { show() }

fun Toast.fixBadTokenException() = apply {
  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
    try {
      @SuppressLint("DiscouragedPrivateApi")
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
}