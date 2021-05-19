@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.graphics.Canvas
import android.graphics.Paint

/**
 * @author Dylan Cai
 */

inline fun Canvas.drawCenterText(text: String, centerX: Float, centerY: Float, paint: Paint) {
  val textAlign = paint.textAlign
  paint.textAlign = Paint.Align.CENTER
  val fontMetrics = paint.fontMetrics
  val baseline = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
  drawText(text, centerX, baseline, paint)
  paint.textAlign = textAlign
}