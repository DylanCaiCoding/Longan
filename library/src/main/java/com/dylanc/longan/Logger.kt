@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.util.Log

/**
 * @author Dylan Cai
 */

interface Logger {
  val loggerTag: String get() = TAG

  companion object {
    var isLoggable = { _: Int, _: String -> true }
    var printer: LoggerPrinter = LonganLoggerPrinter()

    fun init(debug: Boolean) {
      isLoggable = { _, _ -> debug }
    }

    fun init(loggableLevel: LogLevel) {
      isLoggable = { level, _ -> level >= loggableLevel.value }
    }
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
}

val Any.TAG: String get() = javaClass.simpleName.limitLength(23)

inline fun <reified T : Any> Logger(): Logger = object : Logger {
  override val loggerTag: String get() = T::class.java.simpleName.limitLength(23)
}

inline fun Logger(tag: String): Logger = object : Logger {
  override val loggerTag: String get() = tag
}

inline fun Logger.logVerbose(message: Any?, thr: Throwable? = null) = log(LogLevel.VERBOSE, loggerTag, message, thr)

inline fun Logger.logDebug(message: Any?, thr: Throwable? = null) = log(LogLevel.DEBUG, loggerTag, message, thr)

inline fun Logger.logInfo(message: Any?, thr: Throwable? = null) = log(LogLevel.INFO, loggerTag, message, thr)

inline fun Logger.logWarn(message: Any?, thr: Throwable? = null) = log(LogLevel.WARN, loggerTag, message, thr)

inline fun Logger.logError(message: Any?, thr: Throwable? = null) = log(LogLevel.ERROR, loggerTag, message, thr)

inline fun Logger.logWtf(message: Any?, thr: Throwable? = null) = log(LogLevel.ASSERT, loggerTag, message, thr)

inline fun logVerbose(message: Any?, thr: Throwable? = null) = log(LogLevel.VERBOSE, message, thr)

inline fun logDebug(message: Any?, thr: Throwable? = null) = log(LogLevel.DEBUG, message, thr)

inline fun logInfo(message: Any?, thr: Throwable? = null) = log(LogLevel.INFO, message, thr)

inline fun logWarn(message: Any?, thr: Throwable? = null) = log(LogLevel.WARN, message, thr)

inline fun logError(message: Any?, thr: Throwable? = null) = log(LogLevel.ERROR, message, thr)

inline fun logWtf(message: Any?, thr: Throwable? = null) = log(LogLevel.ASSERT, message, thr)

inline fun log(level: LogLevel, message: Any?, thr: Throwable? = null) {
  val tag = Thread.currentThread().stackTrace.find { !it.isIgnorable }?.simpleClassName
  log(level, tag.orEmpty(), message, thr)
}

fun log(level: LogLevel, tag: String, message: Any?, thr: Throwable? = null) {
  if (Logger.isLoggable(level.value, tag)) {
    Logger.printer.log(level, tag, message?.toString() ?: "null", thr)
  }
}

val StackTraceElement.isIgnorable: Boolean
  get() = isNativeMethod || className == Thread::class.java.name || className == Logger::class.java.name

val StackTraceElement.simpleClassName: String?
  get() = className.split(".").run {
    if (isNotEmpty()) last().limitLength(23) else null
  }

class LonganLoggerPrinter : LoggerPrinter {
  override fun log(level: LogLevel, tag: String, message: String, thr: Throwable?) {
    when (level) {
      LogLevel.VERBOSE -> if (thr == null) Log.v(tag, message) else Log.v(tag, message, thr)
      LogLevel.DEBUG -> if (thr == null) Log.d(tag, message) else Log.d(tag, message, thr)
      LogLevel.INFO -> if (thr == null) Log.i(tag, message) else Log.i(tag, message, thr)
      LogLevel.WARN -> if (thr == null) Log.w(tag, message) else Log.w(tag, message, thr)
      LogLevel.ERROR -> if (thr == null) Log.e(tag, message) else Log.e(tag, message, thr)
      LogLevel.ASSERT -> if (thr == null) Log.wtf(tag, message) else Log.wtf(tag, message, thr)
    }
  }
}