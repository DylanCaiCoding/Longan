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

import android.util.Log

fun initLogger(isLoggable: Boolean = true, printer: LoggerPrinter? = null) {
  Logger.isLoggable = { _, _ -> isLoggable }
  printer?.let { Logger.printer = it }
}

interface Logger {
  val loggerTag: String get() = TAG

  companion object {
    var isLoggable = { _: Int, _: String -> true }
    var printer: LoggerPrinter = SimpleLoggerPrinter()
  }
}

@JvmInline
value class LogLevel private constructor(val value: Int) {
  companion object {
    val VERBOSE = LogLevel(Log.VERBOSE)
    val DEBUG = LogLevel(Log.DEBUG)
    val INFO = LogLevel(Log.INFO)
    val WARN = LogLevel(Log.WARN)
    val ERROR = LogLevel(Log.ERROR)
    val ASSERT = LogLevel(Log.ASSERT)
  }
}

interface LoggerPrinter {
  fun log(level: LogLevel, tag: String, message: String, thr: Throwable?)
  fun logWtf(tag: String, message: String, thr: Throwable?)
}

val Any.TAG: String get() = javaClass.simpleName.limitLength(23)

inline val TAG: String
  get() = Thread.currentThread().stackTrace
    .find { !it.isIgnorable }?.simpleClassName.orEmpty()

inline fun <reified T : Any> Logger(): Logger = object : Logger {
  override val loggerTag: String get() = T::class.java.simpleName.limitLength(23)
}

fun Logger(tag: String): Logger = object : Logger {
  override val loggerTag: String get() = tag
}

fun Logger.logVerbose(message: Any?, thr: Throwable? = null) =
  log(LogLevel.VERBOSE, loggerTag, message, thr)

fun Logger.logDebug(message: Any?, thr: Throwable? = null) =
  log(LogLevel.DEBUG, loggerTag, message, thr)

fun Logger.logInfo(message: Any?, thr: Throwable? = null) =
  log(LogLevel.INFO, loggerTag, message, thr)

fun Logger.logWarn(message: Any?, thr: Throwable? = null) =
  log(LogLevel.WARN, loggerTag, message, thr)

fun Logger.logError(message: Any?, thr: Throwable? = null) =
  log(LogLevel.ERROR, loggerTag, message, thr)

fun Logger.logWtf(message: Any?, thr: Throwable? = null) =
  Logger.printer.logWtf(loggerTag, message.toString(), thr)

inline fun logVerbose(message: Any?, thr: Throwable? = null) =
  log(LogLevel.VERBOSE, TAG, message, thr)

inline fun logDebug(message: Any?, thr: Throwable? = null) =
  log(LogLevel.DEBUG, TAG, message, thr)

inline fun logInfo(message: Any?, thr: Throwable? = null) =
  log(LogLevel.INFO, TAG, message, thr)

inline fun logWarn(message: Any?, thr: Throwable? = null) =
  log(LogLevel.WARN, TAG, message, thr)

inline fun logError(message: Any?, thr: Throwable? = null) =
  log(LogLevel.ERROR, TAG, message, thr)

inline fun logWtf(message: Any?, thr: Throwable? = null) =
  Logger.printer.logWtf(TAG, message.toString(), thr)

fun log(level: LogLevel, tag: String, message: Any?, thr: Throwable? = null) {
  if (Logger.isLoggable(level.value, tag)) {
    Logger.printer.log(level, tag, message.toString(), thr)
  }
}

val StackTraceElement.isIgnorable: Boolean
  get() = isNativeMethod || className == Thread::class.java.name || className == Logger::class.java.name

val StackTraceElement.simpleClassName: String?
  get() = className.split(".").run {
    if (isNotEmpty()) last().limitLength(23) else null
  }

open class SimpleLoggerPrinter : LoggerPrinter {
  override fun log(level: LogLevel, tag: String, message: String, thr: Throwable?) {
    when (level) {
      LogLevel.VERBOSE -> if (thr == null) Log.v(tag, message) else Log.v(tag, message, thr)
      LogLevel.DEBUG -> if (thr == null) Log.d(tag, message) else Log.d(tag, message, thr)
      LogLevel.INFO -> if (thr == null) Log.i(tag, message) else Log.i(tag, message, thr)
      LogLevel.WARN -> if (thr == null) Log.w(tag, message) else Log.w(tag, message, thr)
      LogLevel.ERROR -> if (thr == null) Log.e(tag, message) else Log.e(tag, message, thr)
    }
  }

  override fun logWtf(tag: String, message: String, thr: Throwable?) {
    if (thr == null) Log.wtf(tag, message) else Log.wtf(tag, message, thr)
  }
}
