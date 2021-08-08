@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.content.edit
import java.util.*


/**
 * @author Dylan Cai
 */

private const val KEY_LANGUAGE = "longan_language"
private const val KEY_COUNTRY = "longan_country"
private var appLanguageCache: Locale? = null
private val language: String? by sharedPreferences(KEY_LANGUAGE)
private val country: String? by sharedPreferences(KEY_COUNTRY)

val systemLanguage: Locale
  get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    application.resources.configuration.locales[0]
  } else {
    @Suppress("DEPRECATION")
    application.resources.configuration.locale
  }

var appLanguage: Locale
  get() {
    if (appLanguageCache == null) {
      appLanguageCache = language?.let { Locale(it, country.orEmpty()) } ?: systemLanguage
    }
    return appLanguageCache!!
  }
  set(value) {
    appLanguageCache = value
    defaultSharedPreferences.edit {
      putString(KEY_LANGUAGE, value.language)
      putString(KEY_COUNTRY, value.country)
    }
  }

fun resetAppLanguage() {
  appLanguageCache = systemLanguage
  defaultSharedPreferences.edit {
    remove(KEY_LANGUAGE)
    remove(KEY_COUNTRY)
  }
}

fun Context.attachLanguage(): ContextWrapper = wrap(appLanguage)

fun Context.wrap(locale: Locale): ContextWrapper {
  if (systemLanguage != locale) {
    Locale.setDefault(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      resources.configuration.setLocale(locale)
    } else {
      @Suppress("DEPRECATION")
      resources.configuration.locale = locale
    }
  }
  return ContextWrapper(createConfigurationContext(resources.configuration))
}