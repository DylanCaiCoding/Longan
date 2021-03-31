@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import android.util.TypedValue
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI

/**
 * @author Dylan Cai
 */

inline val Long.dp
  get() = toFloat().dp

inline val Int.dp
  get() = toFloat().dp

inline val Float.dp
  get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline fun View.toBitmap(defaultColor: Int = Color.WHITE): Bitmap {
  val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bitmap)
  canvas.drawColor(defaultColor)
  draw(canvas)
  return bitmap
}

fun Bitmap.toPdfFile(
  pathname: String,
  pageWidth: Int = defaultPdfPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  listOf(this).toPdfFile(pathname, pageWidth, pageHeight)

fun Bitmap.toPdfFile(
  parent: String?, child: String,
  pageWidth: Int = defaultPdfPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  listOf(this).toPdfFile(parent, child, pageWidth, pageHeight)

fun Bitmap.toPdfFile(
  parent: File?, child: String,
  pageWidth: Int = defaultPdfPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  listOf(this).toPdfFile(parent, child, pageWidth, pageHeight)

fun Bitmap.toPdfFile(
  uri: URI,
  pageWidth: Int = defaultPdfPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  listOf(this).toPdfFile(uri, pageWidth, pageHeight)

fun List<Bitmap>.toPdfFile(
  pathname: String,
  pageWidth: Int = defaultPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  toPdfFile(File(pathname), pageWidth, pageHeight)

fun List<Bitmap>.toPdfFile(
  parent: String?, child: String,
  pageWidth: Int = defaultPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  toPdfFile(File(parent, child), pageWidth, pageHeight)

fun List<Bitmap>.toPdfFile(
  parent: File?, child: String,
  pageWidth: Int = defaultPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  toPdfFile(File(parent, child), pageWidth, pageHeight)

fun List<Bitmap>.toPdfFile(
  uri: URI,
  pageWidth: Int = defaultPageWidth,
  pageHeight: Int = defaultPdfPageHeight
) =
  toPdfFile(File(uri), pageWidth, pageHeight)

private fun List<Bitmap>.toPdfFile(
  file: File,
  pageWidth: Int,
  pageHeight: Int
): File {
  if (isEmpty() || pageWidth == 0 || pageHeight == 0) {
    return file
  }
  val doc = PdfDocument()
  val scale = pageWidth.toFloat() / this[0].width.toFloat()
  val matrix = Matrix()
  matrix.postScale(scale, scale)
  val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  for (i in this.indices) {
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create()
    val page = doc.startPage(pageInfo)
    page.canvas.drawBitmap(this[i], matrix, paint)
    doc.finishPage(page)
  }
  var outputStream: FileOutputStream? = null
  try {
    outputStream = FileOutputStream(file)
    doc.writeTo(outputStream)
  } catch (e: FileNotFoundException) {
    e.printStackTrace()
  } catch (e: IOException) {
    e.printStackTrace()
  } finally {
    doc.close()
    try {
      outputStream?.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
  return file
}

private val Bitmap.defaultPdfPageWidth
  get() = PrintAttributes.MediaSize.ISO_A4.widthMils * 72 / 1000

private val Bitmap.defaultPdfPageHeight
  get() = (height * defaultPdfPageWidth.toFloat() / width.toFloat()).toInt()

private val List<Bitmap>.defaultPageWidth
  get() = if (this.isEmpty()) 0 else this[0].defaultPdfPageWidth

private val List<Bitmap>.defaultPdfPageHeight
  get() = if (this.isEmpty()) 0 else this[0].defaultPdfPageHeight