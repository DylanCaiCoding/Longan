@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author Dylan Cai
 */

inline fun TextView.isTextEmpty() = text.toString().isEmpty()

inline fun TextView.isTextNotEmpty() = text.toString().isNotEmpty()

fun TextView.startCountDown(
  lifecycleOwner: LifecycleOwner,
  millisInFuture: Long = 60 * 1000,
  countDownInterval: Long = 1000,
  onTick: TextView.(millisUntilFinished: Long) -> Unit,
  onFinish: TextView.() -> Unit,
) {
  val countDownTimer = object : CountDownTimer(millisInFuture, countDownInterval) {
    override fun onTick(millisUntilFinished: Long) {
      isEnabled = false
      this@startCountDown.onTick(millisUntilFinished / 1000 + 1)
    }

    override fun onFinish() {
      isEnabled = true
      this@startCountDown.onFinish()
    }
  }
  countDownTimer.start()
  lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
      countDownTimer.cancel()
    }
  })
}

inline fun TextView.enableWhenTextNotEmpty(vararg textViews: TextView) {
  textViews.forEach {
    it.doAfterTextChanged {
      var isAllNotEmpty = true
      for (textView in textViews) {
        if (textView.isTextEmpty()) {
          isAllNotEmpty = false
          break
        }
      }
      isEnabled = isAllNotEmpty
    }
  }
}

inline var TextView.isPasswordVisible: Boolean
  get() = transformationMethod != PasswordTransformationMethod.getInstance()
  set(value) {
    transformationMethod = if (value) {
      HideReturnsTransformationMethod.getInstance()
    } else {
      PasswordTransformationMethod.getInstance()
    }
  }