@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

/**
 * @author Dylan Cai
 */

const val XML = 0
const val JSON = 1
const val VERBOSE = 2
const val DEBUG = 3
const val INFO = 4
const val WARN = 5
const val ERROR = 6
const val ASSERT = 7

interface Logger {
  val loggerTag: String get() = TAG
}

inline fun Logger(tag: String): Logger = object : Logger {
  override val loggerTag: String get() = tag
}

object LoggerConfig {
  var isLoggable = { _: Int, _: String? -> true }

  var printer: LoggerPrinter = GrapeLoggerPrinter()
}

inline val Any.loggerTag: String
  get() = if (this is Logger) loggerTag else TAG

/**
 * Returns the simple class name.
 */
inline val Any.TAG: String
  get() = javaClass.simpleName.let { tag ->
    if (tag.length <= 23) tag else tag.substring(0, 23)
  }

inline fun Logger.logVerbose(message: String) = log(VERBOSE, loggerTag, message)

inline fun Logger.logDebug(message: String) = log(DEBUG, loggerTag, message)

inline fun Logger.logInfo(message: String) = log(INFO, loggerTag, message)

inline fun Logger.logWarn(message: String) = log(WARN, loggerTag, message)

inline fun Logger.logError(message: String) = log(ERROR, loggerTag, message)

inline fun Logger.logAssert(message: String) = log(ASSERT, loggerTag, message)

inline fun Logger.logJson(json: String) = log(JSON, loggerTag, json)

inline fun Logger.logXml(content: String) = log(XML, loggerTag, content)

inline fun Any.logVerbose(message: String) = log(VERBOSE, loggerTag, message)

inline fun Any.logDebug(message: String) = log(DEBUG, loggerTag, message)

inline fun Any.logInfo(message: String) = log(INFO, loggerTag, message)

inline fun Any.logWarn(message: String) = log(WARN, loggerTag, message)

inline fun Any.logError(message: String) = log(ERROR, loggerTag, message)

inline fun Any.logAssert(message: String) = log(ASSERT, loggerTag, message)

inline fun Any.logJson(json: String) = log(JSON, loggerTag, json)

inline fun Any.logXml(content: String) = log(XML, loggerTag, content)

inline fun logVerbose(message: String) = log(VERBOSE, null, message)

inline fun logDebug(message: String) = log(DEBUG, null, message)

inline fun logInfo(message: String) = log(INFO, null, message)

inline fun logWarn(message: String) = log(WARN, null, message)

inline fun logError(message: String) = log(ERROR, null, message)

inline fun logAssert(message: String) = log(ASSERT, null, message)

inline fun logJson(json: String) = log(JSON, null, json)

inline fun logXml(content: String) = log(XML, null, content)

inline fun log(priority: Int, tag: String?, message: String) {
  if (LoggerConfig.isLoggable(priority, tag)) {
    LoggerConfig.printer.log(priority, tag, message)
  }
}

interface LoggerPrinter {
  fun log(priority: Int, tag: String?, message: String)
}

typealias Log = com.orhanobut.logger.Logger

class GrapeLoggerPrinter : LoggerPrinter {
  override fun log(priority: Int, tag: String?, message: String) {
    when (priority) {
      XML -> if (tag != null) Log.t(tag).xml(message) else Log.xml(message)
      JSON -> if (tag != null) Log.t(tag).json(message) else Log.json(message)
      VERBOSE -> if (tag != null) Log.t(tag).v(message) else Log.v(message)
      DEBUG -> if (tag != null) Log.t(tag).d(message) else Log.d(message)
      INFO -> if (tag != null) Log.t(tag).i(message) else Log.i(message)
      WARN -> if (tag != null) Log.t(tag).w(message) else Log.w(message)
      ERROR -> if (tag != null) Log.t(tag).e(message) else Log.e(message)
      ASSERT -> if (tag != null) Log.t(tag).wtf(message) else Log.wtf(message)
    }
  }
}