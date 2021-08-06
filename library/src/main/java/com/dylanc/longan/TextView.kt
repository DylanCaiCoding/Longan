@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.graphics.Paint
import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.math.roundToInt

/**
 * @author Dylan Cai
 */

inline val TextView.textString: String get() = text.toString()

inline fun TextView.isTextEmpty() = textString.isEmpty()

inline fun TextView.isTextNotEmpty() = textString.isNotEmpty()

fun TextView.startCountDown(
  lifecycleOwner: LifecycleOwner,
  secondInFuture: Int = 60,
  onTick: TextView.(secondUntilFinished: Int) -> Unit,
  onFinish: TextView.() -> Unit,
) {
  val countDownTimer = object : CountDownTimer(secondInFuture * 1000L, 1000) {
    override fun onTick(millisUntilFinished: Long) {
      isEnabled = false
      onTick((millisUntilFinished / 1000f).roundToInt())
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

inline fun TextView.showUnderline() {
  paint.flags = Paint.UNDERLINE_TEXT_FLAG
}