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

import android.os.Build
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import java.util.concurrent.Executors
import java.util.regex.Pattern

fun initLogger(
    isLoggable: Boolean = true,
    printer: LoggerPrinter? = null,
    isLog2File: Boolean = false,
    fileWriter: FileWriter? = null,
) {
    Logger.isLoggable = { _, _ -> isLoggable }
    Logger.isLog2File = isLog2File
    fileWriter?.let {
        Logger.fileWriter = it
    }
    printer?.let { Logger.printer = it }
}

interface Logger {
    val loggerTag: String get() = TAG

    companion object {
        var isLoggable = { _: Int, _: String -> true }
        var isLog2File = true
        var printer: LoggerPrinter = SimpleLoggerPrinter()
        var fileWriter: FileWriter = SimpleFileWriter()
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

interface FileWriter {
    fun write(level: LogLevel, tag: String, message: String, thr: Throwable?)
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
    if (Logger.isLog2File) {
        Executors.newSingleThreadExecutor().execute {
            Logger.fileWriter.write(level, tag, message.toString(), thr)
        }
    }
}

val StackTraceElement.isIgnorable: Boolean
    get() = isNativeMethod || className == Thread::class.java.name || className == Logger::class.java.name

val StackTraceElement.simpleClassName: String?
    get() = className.split(".").run {
        if (isNotEmpty()) last().limitLength(23) else null
    }

open class SimpleLoggerPrinter : LoggerPrinter {

    private val maxLin = 1100
    private val LINE_SEP = System.getProperty("line.separator") ?: "\n"
    private val TOP_CORNER = "┌"
    private val MIDDLE_CORNER = "├"
    private val LEFT_BORDER = "│ "
    private val BOTTOM_CORNER = "└"
    private val SIDE_DIVIDER = "────────────────────────────────────────────────────────"
    private val MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
    private val TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
    private val MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER
    private val BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER
    private val PLACEHOLDER = " "


    override fun log(level: LogLevel, tag: String, message: String, thr: Throwable?) {
        var msg22 = message
        if (thr != null) {
            msg22 += '\n' + Log.getStackTraceString(thr)
        }
        val processMsg = processMsg(msg22)

        val len = processMsg.length
        val countOfSub = (len - BOTTOM_BORDER.length) / maxLin
        if (countOfSub > 0) {
            Log.println(
                level.value,
                tag,
                processMsg.substring(0, maxLin) + LINE_SEP + BOTTOM_BORDER
            )
            var index = maxLin
            for (i in 1 until countOfSub) {
                val subMsg = processMsg.substring(index, index + maxLin)
                Log.println(
                    level.value,
                    tag,
                    PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP + LEFT_BORDER + subMsg + LINE_SEP + BOTTOM_BORDER
                )
                index += maxLin
            }
            if (index != len - BOTTOM_BORDER.length) {
                val subMsg = processMsg.substring(index, len)
                Log.println(
                    level.value,
                    tag,
                    PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP + LEFT_BORDER + subMsg
                )
            }
        } else {
            Log.println(level.value, tag, processMsg)
        }

    }

    override fun logWtf(tag: String, message: String, thr: Throwable?) {
        if (thr == null) Log.wtf(tag, message) else Log.wtf(tag, message, thr)
    }

    private fun processMsg(msg: String): String {
        val sb = StringBuilder()
        sb.append(PLACEHOLDER).append(LINE_SEP)
            .append(TOP_BORDER).append(LINE_SEP)
        val split = msg.split(LINE_SEP)
        split.forEach {
            sb.append(LEFT_BORDER).append(it).append(LINE_SEP)
        }
        sb.append(BOTTOM_BORDER)
        return sb.toString()
    }
}

open class SimpleFileWriter : FileWriter {
    private val saveDays = 10
    private val LINE_SEP = System.getProperty("line.separator")
    private val type = charArrayOf('V', 'D', 'I', 'W', 'E', 'A')
    override fun write(level: LogLevel, tag: String, message: String, thr: Throwable?) {
        val format = Instant.now().format("yyyy_MM_dd HH:mm:ss.SSS ")
        val date = format.substring(0, 10)
        val time = format.substring(11)
        val rootDir = application.filesDir.absolutePath + "/log/"
        val fileName = "log_" + date + "_" + packageName + ".txt"
        val logFile = File(rootDir, fileName)
        //查看文件是否存在
        if (!logFile.exists()) {
            //删除过期的文件
            deleteDueLogs(logFile, date)
            val isCreate = logFile.isExistOrCreateNewFile()
            if (isCreate) {
                printDeviceInfo(logFile, date)
            }
        }

        val typeLow = level.value and 0x0f
        val content: String =
            time + type[typeLow - LogLevel.VERBOSE.value] + "/" + tag + "  " + message + LINE_SEP
        logFile.appendText(content)
    }

    private fun printDeviceInfo(logFile: File, date: String) {
        val versionName = appVersionName
        val versionCode = appVersionCode
        val head = """
            ************* Log Head ****************
            Date of Log        : $date
            Device Manufacturer: ${Build.MANUFACTURER}
            Device Model       : ${Build.MODEL}
            Android Version    : ${Build.VERSION.RELEASE}
            Android SDK        : ${Build.VERSION.SDK_INT}
            app VersionName    : $versionName
            app VersionCode    : $versionCode
            ************* Log Head ****************
            
            
            """.trimIndent()

        logFile.appendText(head)
    }

    private fun deleteDueLogs(filePath: File, date: String) {
        val listFiles = filePath.parentFile?.listFiles { _, name -> isMatchLogFileName(name) }
        if (listFiles == null || listFiles.isEmpty())
            return
        val sdf = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
        val dueMillis = (sdf.parse(date)?.time ?: 0) - saveDays * 86400000L

        listFiles.forEach {
            val logDay = findDate(it.name)
            if ((sdf.parse(logDay)?.time ?: 0) <= dueMillis) {
                val delete = it.delete()
                if (!delete) {
                    Log.e("LogUtils", "delete $it failed!")
                }
            }
        }
    }

    private fun findDate(name: String): String {
        name.matches(Regex("[0-9]{4}_[0-9]{2}_[0-9]{2}"))
        val matcher = Pattern.compile("[0-9]{4}_[0-9]{2}_[0-9]{2}").matcher(name)
        if (matcher.find()) {
            return matcher.group()
        }
        return ""
    }

    private fun isMatchLogFileName(name: String): Boolean {
        return name.matches(Regex("^log_[0-9]{4}_[0-9]{2}_[0-9]{2}_.*$"))
    }

}
