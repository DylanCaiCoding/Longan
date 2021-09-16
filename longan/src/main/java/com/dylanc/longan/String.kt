@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.text.format.Formatter
import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.util.*

/**
 * @author Dylan Cai
 */
const val REGEX_ID_CARD_15: String = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"

const val REGEX_ID_CARD_18: String = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$"

inline val randomUUIDString: String get() = UUID.randomUUID().toString()

inline fun Long.toFileSizeString(): String = Formatter.formatFileSize(application, this)

inline fun Long.toShortFileSizeString(): String = Formatter.formatShortFileSize(application, this)

inline fun String.limitLength(length: Int) = if (this.length <= length) this else substring(0, length)

inline fun String.isPhone() = Patterns.PHONE.matcher(this).matches()

inline fun String.isDomainName() = PatternsCompat.DOMAIN_NAME.matcher(this).matches()

inline fun String.isEmail() = PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

inline fun String.isIP() = PatternsCompat.IP_ADDRESS.matcher(this).matches()

/**
 *  Regular expression pattern to match most part of RFC 3987
 *  Internationalized URLs, aka IRIs.
 */
inline fun String.isWebUrl() = PatternsCompat.WEB_URL.matcher(this).matches()

inline fun String.isIDCard15() = REGEX_ID_CARD_15.toRegex().matches(this)

inline fun String.isIDCard18() = REGEX_ID_CARD_18.toRegex().matches(this)
