package com.dylanc.longan

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

/**
 * @author Dylan Cai
 */


inline fun printTo(file: File, block: PrintWriter.() -> Unit) =
  PrintWriter(BufferedWriter(FileWriter(file))).apply(block).close()