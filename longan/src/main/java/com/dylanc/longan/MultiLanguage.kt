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

@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import java.util.*

private var appLanguageCache: Locale? = null

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
      appLanguageCache = LanguageManager.appLanguage
    }
    return appLanguageCache!!
  }
  set(value) {
    appLanguageCache = value
    LanguageManager.appLanguage = value
  }

fun resetAppLanguage() {
  appLanguageCache = systemLanguage
  LanguageManager.reset()
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

private object LanguageManager {
  private const val KEY_LANGUAGE = "longan_language"
  private const val KEY_COUNTRY = "longan_country"
  private val sharedPreferences: SharedPreferences =
    application.getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)

  var appLanguage: Locale
    get() = language?.let { Locale(it, country.orEmpty()) } ?: systemLanguage
    set(value) {
      sharedPreferences.edit {
        putString(KEY_LANGUAGE, value.language)
        putString(KEY_COUNTRY, value.country)
      }
    }

  fun reset() {
    sharedPreferences.edit {
      remove(KEY_LANGUAGE)
      remove(KEY_COUNTRY)
    }
  }

  private val language: String?
    get() = sharedPreferences.getString(KEY_LANGUAGE, null)

  private val country: String?
    get() = sharedPreferences.getString(KEY_COUNTRY, null)
}
