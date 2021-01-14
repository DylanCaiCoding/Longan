@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException

/**
 * @author Dylan Cai
 */

val sp: SharedPreferences by lazy { spOf() }

fun spOf(name: String = packageName, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun <T> putSpValue(key: String, value: T, sharedPreferences: SharedPreferences = sp) {
  sharedPreferences.edit().apply {
    when (value) {
      is Long -> putLong(key, value)
      is String -> putString(key, value)
      is Int -> putInt(key, value)
      is Boolean -> putBoolean(key, value)
      is Float -> putFloat(key, value)
      else -> throw IllegalArgumentException()
    }
  }.apply()
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> spValueOf(key: String, default: T, sharedPreferences: SharedPreferences = sp) =
  sharedPreferences.run {
    when (default) {
      is Long -> getLong(key, default)
      is String -> getString(key, default)
      is Int -> getInt(key, default)
      is Boolean -> getBoolean(key, default)
      is Float -> getFloat(key, default)
      else -> {
        val valueType = default.javaClass.canonicalName
        throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
      }
    } as T
  }