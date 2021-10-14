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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import java.io.File
import java.io.FileOutputStream

/**
 * @author Dylan Cai
 */

inline fun Bitmap.toA4PdfDoc(): PdfDocument =
  listOf(this).toA4PdfDoc()

inline fun List<Bitmap>.toA4PdfDoc(): PdfDocument {
  val pageWidth: Int = PrintAttributes.MediaSize.ISO_A4.widthMils * 72 / 1000
  val pageHeight: Int = if (isNotEmpty()) first().height * pageWidth / first().width else 0
  return toPdfDoc(pageWidth, pageHeight)
}

inline fun Bitmap.toPdfDoc(pageWidth: Int, pageHeight: Int): PdfDocument =
  listOf(this).toPdfDoc(pageWidth, pageHeight)

fun List<Bitmap>.toPdfDoc(pageWidth: Int, pageHeight: Int): PdfDocument =
  PdfDocument().also { doc ->
    if (isEmpty()) return@also
    val scale = pageWidth.toFloat() / first().width.toFloat()
    val matrix = Matrix()
    matrix.postScale(scale, scale)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    for (i in this.indices) {
      val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create()
      val page = doc.startPage(pageInfo)
      page.canvas.drawBitmap(this[i], matrix, paint)
      doc.finishPage(page)
    }
  }

inline fun PdfDocument.writeTo(file: File) {
  FileOutputStream(file).use {
    writeTo(it)
  }
  close()
}
