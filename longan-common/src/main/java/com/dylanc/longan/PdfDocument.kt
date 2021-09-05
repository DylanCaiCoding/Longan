@file:Suppress("unused","NOTHING_TO_INLINE")

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

inline fun Bitmap.toA4PdfDoc() = listOf(this).toA4PdfDoc()

inline fun List<Bitmap>.toA4PdfDoc(): PdfDocument {
  val pageWidth: Int = PrintAttributes.MediaSize.ISO_A4.widthMils * 72 / 1000
  val pageHeight: Int = if (isNotEmpty()) first().height * pageWidth / first().width else 0
  return toPdfDoc(pageWidth, pageHeight)
}

inline fun Bitmap.toPdfDoc(pageWidth: Int, pageHeight: Int) =
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

fun PdfDocument.writeTo(file: File) {
  FileOutputStream(file).use {
    writeTo(it)
  }
  close()
}
