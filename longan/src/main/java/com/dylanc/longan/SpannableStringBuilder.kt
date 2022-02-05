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

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.text.inSpans

private const val IMAGE_SPAN_TEXT = "<img/>"
private const val SPACE_SPAN_TEXT = "<space/>"

inline fun SpannableStringBuilder.size(
  size: Float,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = size(size.toInt(), builderAction)

inline fun SpannableStringBuilder.size(
  size: Int,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(AbsoluteSizeSpan(size), builderAction)

inline fun SpannableStringBuilder.alignCenter(
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = alignment(Layout.Alignment.ALIGN_CENTER, builderAction)

inline fun SpannableStringBuilder.alignOpposite(
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = alignment(Layout.Alignment.ALIGN_OPPOSITE, builderAction)

inline fun SpannableStringBuilder.alignment(
  alignment: Layout.Alignment,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(AlignmentSpan.Standard(alignment), builderAction)

inline fun SpannableStringBuilder.blur(
  radius: Float,
  style: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = maskFilter(BlurMaskFilter(radius, style), builderAction)

inline fun SpannableStringBuilder.maskFilter(
  filter: MaskFilter,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(MaskFilterSpan(filter), builderAction)

inline fun SpannableStringBuilder.fontFamily(
  family: String?,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(TypefaceSpan(family), builderAction)

inline fun SpannableStringBuilder.typeface(
  typeface: Typeface,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(TypefaceSpanCompat(typeface), builderAction)

inline fun SpannableStringBuilder.url(
  url: String,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(URLSpan(url), builderAction)

inline fun SpannableStringBuilder.bullet(
  gapWidth: Float,
  @ColorInt color: Int? = null,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = bullet(gapWidth.toInt(), color, builderAction)

inline fun SpannableStringBuilder.bullet(
  gapWidth: Int = BulletSpan.STANDARD_GAP_WIDTH,
  @ColorInt color: Int? = null,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
  val span = if (color == null) BulletSpan(gapWidth) else BulletSpan(gapWidth, color)
  return inSpans(span, builderAction)
}

inline fun SpannableStringBuilder.quote(
  @ColorInt color: Int? = null,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder {
  val span = if (color == null) QuoteSpan() else QuoteSpan(color)
  return inSpans(span, builderAction)
}

inline fun SpannableStringBuilder.leadingMargin(
  first: Float,
  rest: Float = first,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = leadingMargin(first.toInt(), rest.toInt(), builderAction)

inline fun SpannableStringBuilder.leadingMargin(
  first: Int,
  rest: Int = first,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(LeadingMarginSpan.Standard(first, rest), builderAction)

fun SpannableStringBuilder.clickable(
  @ColorInt color: Int? = null,
  isUnderlineText: Boolean = true,
  onClick: (View) -> Unit,
  builderAction: SpannableStringBuilder.() -> Unit
): SpannableStringBuilder = inSpans(object : ClickableSpan() {
  override fun onClick(widget: View) {
    onClick(widget)
  }

  override fun updateDrawState(ds: TextPaint) {
    ds.color = color ?: ds.linkColor
    ds.isUnderlineText = isUnderlineText
  }
}, builderAction)

fun SpannableStringBuilder.append(
  drawable: Drawable,
  width: Int = drawable.intrinsicWidth,
  height: Int = drawable.intrinsicHeight
): SpannableStringBuilder {
  drawable.setBounds(0, 0, width, height)
  return inSpans(ImageSpan(drawable)) { append(IMAGE_SPAN_TEXT) }
}

fun SpannableStringBuilder.append(
  @DrawableRes resourceId: Int,
  context: Context = application
): SpannableStringBuilder = inSpans(ImageSpan(context, resourceId)) { append(IMAGE_SPAN_TEXT) }

fun SpannableStringBuilder.append(
  bitmap: Bitmap,
  context: Context = application
): SpannableStringBuilder = inSpans(ImageSpan(context, bitmap)) { append(IMAGE_SPAN_TEXT) }

fun SpannableStringBuilder.appendClickable(
  text: CharSequence?,
  @ColorInt color: Int? = null,
  isUnderlineText: Boolean = true,
  onClick: (View) -> Unit
): SpannableStringBuilder = clickable(color, isUnderlineText, onClick) { append(text) }

fun SpannableStringBuilder.appendClickable(
  drawable: Drawable,
  width: Int = drawable.intrinsicWidth,
  height: Int = drawable.intrinsicHeight,
  onClick: (View) -> Unit
): SpannableStringBuilder = clickable(onClick = onClick) { append(drawable, width, height) }

fun SpannableStringBuilder.appendClickable(
  @DrawableRes resourceId: Int,
  context: Context = application,
  onClick: (View) -> Unit
): SpannableStringBuilder = clickable(onClick = onClick) { append(resourceId, context) }

fun SpannableStringBuilder.appendClickable(
  bitmap: Bitmap,
  context: Context = application,
  onClick: (View) -> Unit
): SpannableStringBuilder = clickable(onClick = onClick) { append(bitmap, context) }

fun SpannableStringBuilder.appendSpace(
  @androidx.annotation.FloatRange(from = 0.0) size: Float,
  @ColorInt color: Int = Color.TRANSPARENT
): SpannableStringBuilder = appendSpace(size.toInt(), color)

fun SpannableStringBuilder.appendSpace(
  @androidx.annotation.IntRange(from = 0) size: Int,
  @ColorInt color: Int = Color.TRANSPARENT
): SpannableStringBuilder = inSpans(SpaceSpan(size, color)) { append(SPACE_SPAN_TEXT) }

class SpaceSpan constructor(private val width: Int, color: Int = Color.TRANSPARENT) : ReplacementSpan() {
  private val paint = Paint().apply {
    this.color = color
    style = Paint.Style.FILL
  }

  override fun getSize(
    paint: Paint, text: CharSequence?,
    @androidx.annotation.IntRange(from = 0) start: Int,
    @androidx.annotation.IntRange(from = 0) end: Int,
    fm: Paint.FontMetricsInt?
  ): Int = width

  override fun draw(
    canvas: Canvas, text: CharSequence?,
    @androidx.annotation.IntRange(from = 0) start: Int,
    @androidx.annotation.IntRange(from = 0) end: Int,
    x: Float, top: Int, y: Int, bottom: Int, paint: Paint
  ) =
    canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), this.paint)
}

class TypefaceSpanCompat(private val newType: Typeface) : TypefaceSpan(null) {
  override fun updateDrawState(ds: TextPaint) {
    ds.applyTypeFace(newType)
  }

  override fun updateMeasureState(paint: TextPaint) {
    paint.applyTypeFace(newType)
  }

  private fun TextPaint.applyTypeFace(tf: Typeface) {
    val oldStyle: Int
    val old = typeface
    oldStyle = old?.style ?: 0
    val fake = oldStyle and tf.style.inv()
    if (fake and Typeface.BOLD != 0) {
      isFakeBoldText = true
    }
    if (fake and Typeface.ITALIC != 0) {
      textSkewX = -0.25f
    }
    typeface = tf
  }
}
