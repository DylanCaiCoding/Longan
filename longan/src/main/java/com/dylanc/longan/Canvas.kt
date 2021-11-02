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

import android.graphics.Canvas
import android.graphics.Paint


fun Canvas.drawCenterVerticalText(text: String, centerX: Float, centerY: Float, paint: Paint) =
  drawCenterText(text, centerX, centerY, paint, Paint.Align.LEFT)

fun Canvas.drawCenterText(
  text: String,
  centerX: Float,
  centerY: Float,
  paint: Paint,
  textAlign: Paint.Align = Paint.Align.CENTER
) {
  val textAlignTemp = paint.textAlign
  paint.textAlign = textAlign
  val fontMetrics = paint.fontMetrics
  val baseline = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
  drawText(text, centerX, baseline, paint)
  paint.textAlign = textAlignTemp
}
