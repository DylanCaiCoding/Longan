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

inline fun logVerbose(message: String) = logVerbose(null, message)

inline fun logDebug(message: String) = logDebug(null, message)

inline fun logInfo(message: String) = logInfo(null, message)

inline fun logWarn(message: String) = logWarn(null, message)

inline fun logError(message: String) = logError(null, message)

inline fun logAssert(message: String) = logAssert(null, message)

inline fun logJson(json: String) = logJson(null, json)

inline fun logXml(content: String) = logXml(null, content)

inline fun logVerbose(tag: String?, message: String) = log(VERBOSE, tag, message)

inline fun logDebug(tag: String?, message: String) = log(DEBUG, tag, message)

inline fun logInfo(tag: String?, message: String) = log(INFO, tag, message)

inline fun logWarn(tag: String?, message: String) = log(WARN, tag, message)

inline fun logError(tag: String?, message: String) = log(ERROR, tag, message)

inline fun logAssert(tag: String?, message: String) = log(ASSERT, tag, message)

inline fun logJson(tag: String?, json: String) = log(JSON, tag, json)

inline fun logXml(tag: String?, content: String) = log(XML, tag, content)

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
      XML -> Log.t(tag).xml(message)
      JSON -> Log.t(tag).json(message)
      VERBOSE -> Log.t(tag).v(message)
      DEBUG -> Log.t(tag).d(message)
      INFO -> Log.t(tag).i(message)
      WARN -> Log.t(tag).w(message)
      ERROR -> Log.t(tag).e(message)
      ASSERT -> Log.t(tag).wtf(message)
    }
  }
}