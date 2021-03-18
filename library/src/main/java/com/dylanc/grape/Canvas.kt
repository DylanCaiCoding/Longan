@file:Suppress("unused")

package com.dylanc.grape

import android.graphics.Canvas
import android.graphics.Paint

/**
 * @author Dylan Cai
 */

fun Canvas.drawCenterText(text: String, centerX: Float, centerY: Float, paint: Paint) {
  val fontMetrics: Paint.FontMetrics = paint.fontMetrics
  val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
  val baseline: Float = centerY + distance
  drawText(text, centerX, baseline, paint)
}