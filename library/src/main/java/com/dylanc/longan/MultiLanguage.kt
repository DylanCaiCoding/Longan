@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import androidx.core.content.edit
import java.util.*


/**
 * @author Dylan Cai
 */

val systemLanguage: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
  application.resources.configuration.locales[0]
} else {
  @Suppress("DEPRECATION")
  application.resources.configuration.locale
}

fun Context.attachLanguage(): ContextWrapper = wrap(LanguageManager.language)

fun Context.wrap(locale: Locale): ContextWrapper {
  language = locale
  return ContextWrapper(createConfigurationContext(resources.configuration))
}

internal var Context.language: Locale
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    if (systemLanguage != value) {
      Locale.setDefault(value)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.setLocale(value)
      } else {
        @Suppress("DEPRECATION")
        resources.configuration.locale = value
      }
    }
  }

var appLanguage: Locale
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    LanguageManager.language = value
  }

var multiLanguage: Boolean
  @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
  get() = noGetter()
  set(value) {
    if (value) {
      application.registerActivityLifecycleCallbacks(ActivityLanguageCallbacks())
    }
  }

object LanguageManager : SharedPreferencesOwner {
  private const val KEY_LANGUAGE = "longan_language"
  private const val KEY_COUNTRY = "longan_country"

  @Suppress("ObjectPropertyName")
  private var _language: Locale? = null

  var language: Locale
    get() {
      if (_language == null) {
        val locale = sharedPreferences.getString(KEY_LANGUAGE, null)?.let { language ->
          val country = sharedPreferences.getString(KEY_COUNTRY, null)
          Locale(language, country.orEmpty())
        }
        _language = locale ?: systemLanguage
      }
      return _language!!
    }
    set(value) {
      _language = value
      sharedPreferences.edit {
        putString(KEY_LANGUAGE, value.language)
        putString(KEY_COUNTRY, value.country)
      }
    }

  fun clear() {
    _language = systemLanguage
    sharedPreferences.edit {
      remove(KEY_LANGUAGE)
      remove(KEY_COUNTRY)
    }
  }
}

internal class ActivityLanguageCallbacks : ActivityLifecycleCallbacks {
  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    activity.language = LanguageManager.language
    application.resources.updateConfiguration(
      application.resources.configuration,
      application.resources.displayMetrics
    )
  }

  override fun onActivityStarted(activity: Activity) {}
  override fun onActivityResumed(activity: Activity) {
    activity.language = LanguageManager.language
    application.resources.updateConfiguration(
      application.resources.configuration,
      application.resources.displayMetrics
    )
  }

  override fun onActivityPaused(activity: Activity) {}
  override fun onActivityStopped(activity: Activity) {}
  override fun onActivityDestroyed(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
}