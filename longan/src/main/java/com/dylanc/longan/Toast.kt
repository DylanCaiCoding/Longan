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

fun Fragment.toast(message: CharSequence?): Toast =
  requireActivity().toast(message)

fun Fragment.toast(@StringRes message: Int): Toast =
  requireActivity().toast(message)

fun Context.toast(message: CharSequence?): Toast =
  Toast.makeText(this, message, Toast.LENGTH_SHORT).fixBadTokenException().apply { show() }

fun Context.toast(@StringRes message: Int): Toast =
  Toast.makeText(this, message, Toast.LENGTH_SHORT).fixBadTokenException().apply { show() }

fun Fragment.longToast(message: CharSequence?): Toast =
  requireActivity().longToast(message)

fun Fragment.longToast(@StringRes message: Int): Toast =
  requireActivity().longToast(message)

fun Context.longToast(message: CharSequence?): Toast =
  Toast.makeText(this, message, Toast.LENGTH_LONG).fixBadTokenException().apply { show() }

fun Context.longToast(@StringRes message: Int): Toast =
  Toast.makeText(this, message, Toast.LENGTH_LONG).fixBadTokenException().apply { show() }

fun Toast.fixBadTokenException(): Toast = apply {
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
