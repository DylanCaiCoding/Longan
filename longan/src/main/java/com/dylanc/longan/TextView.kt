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

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import kotlin.math.roundToInt

inline val TextView.textString: String get() = text.toString()

fun TextView.isTextEmpty(): Boolean = textString.isEmpty()

fun TextView.isTextNotEmpty(): Boolean = textString.isNotEmpty()

inline var TextView.isPasswordVisible: Boolean
  get() = transformationMethod != PasswordTransformationMethod.getInstance()
  set(value) {
    transformationMethod = if (value) {
      HideReturnsTransformationMethod.getInstance()
    } else {
      PasswordTransformationMethod.getInstance()
    }
  }

inline var TextView.isFakeBoldText: Boolean
  get() = paint.isFakeBoldText
  set(value) {
    paint.isFakeBoldText = value
  }

var TextView.font: Int
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(@FontRes value) {
    typeface = context.getCompatFont(value)
  }

var TextView.typefaceFromAssets: String
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    typeface = Typeface.createFromAsset(context.assets, value)
  }

fun TextView.addUnderline() {
  paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.transparentHighlightColor() {
  highlightColor = Color.TRANSPARENT
}

fun TextView.startCountDown(
  lifecycleOwner: LifecycleOwner,
  secondInFuture: Int = 60,
  onTick: TextView.(secondUntilFinished: Int) -> Unit,
  onFinish: TextView.() -> Unit,
): CountDownTimer =
  object : CountDownTimer(secondInFuture * 1000L, 1000) {
    override fun onTick(millisUntilFinished: Long) {
      isEnabled = false
      onTick((millisUntilFinished / 1000f).roundToInt())
    }

    override fun onFinish() {
      isEnabled = true
      this@startCountDown.onFinish()
    }
  }.also { countDownTimer ->
    countDownTimer.start()
    lifecycleOwner.doOnLifecycle(onDestroy = {
      countDownTimer.cancel()
    })
  }

fun TextView.enableWhenOtherTextNotEmpty(vararg textViews: TextView) =
  enableWhenOtherTextChanged(*textViews) { all { it.isTextNotEmpty() } }

inline fun TextView.enableWhenOtherTextChanged(
  vararg textViews: TextView,
  crossinline block: Array<out TextView>.() -> Boolean
) {
  isEnabled = block(textViews)
  textViews.forEach { tv ->
    tv.doAfterTextChanged {
      isEnabled = block(textViews)
    }
  }
}

fun TextView.enableWhenAllChecked(vararg checkBoxes: CheckBox) {
  isEnabled = checkBoxes.all { it.isChecked }
  checkBoxes.forEach { cb ->
    cb.setOnCheckedChangeListener { _, _ ->
      isEnabled = checkBoxes.all { it.isChecked }
    }
  }
}
