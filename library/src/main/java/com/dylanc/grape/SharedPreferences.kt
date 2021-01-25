@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Dylan Cai
 */

val defaultSharedPreferences: SharedPreferences by lazy { sharedPreferencesOf() }

fun sharedPreferencesOf(name: String = packageName, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun <T : Any> Any.sharedPreferences(key: String, default: T) =
  SharedPreferencesValueDelegate(key, default, sharedPreferencesOwner.sharedPreferences)

fun <T : Any> SharedPreferencesOwner.sharedPreferences(key: String, default: T) =
  SharedPreferencesValueDelegate(key, default, sharedPreferences)

private val Any.sharedPreferencesOwner: SharedPreferencesOwner
  get() = object : SharedPreferencesOwner {}

fun <T> SharedPreferences.putValue(key: String, value: T) {
  edit().apply {
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
fun <T : Any> SharedPreferences.getValue(key: String, default: T) =
  run {
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

interface SharedPreferencesOwner {
  val sharedPreferences: SharedPreferences get() = defaultSharedPreferences
}

class SharedPreferencesValueDelegate<T : Any>(
  private val key: String,
  private val default: T,
  private val sharedPreferences: SharedPreferences = defaultSharedPreferences
) : ReadWriteProperty<Any, T> {

  override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
    sharedPreferences.putValue(key, value)
  }

  override fun getValue(thisRef: Any, property: KProperty<*>): T {
    return sharedPreferences.getValue(key, default)
  }
}