@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Dylan Cai
 */

var defaultSharedPreferences = sharedPreferencesOf()
  private set

fun initSharedPreferences(name: String, mode: Int = Context.MODE_PRIVATE) {
  defaultSharedPreferences = sharedPreferencesOf(name, mode)
}

fun sharedPreferencesOf(name: String = "${packageName}_preferences", mode: Int = Context.MODE_PRIVATE): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun <V : Any> sharedPreferences(key: String, default: V) =
  SharedPrefValueWithDefault<Nothing?, V>(default, defaultSharedPreferences) { key }

fun sharedPreferences(key: String) =
  SharedPrefStringValue<Nothing?>(defaultSharedPreferences) { key }

fun <V : Any> Any.sharedPreferences(key: String, default: V) =
  SharedPrefValueWithDefault<Any, V>(default, defaultSharedPreferences) { key }

fun Any.sharedPreferences(key: String) =
  SharedPrefStringValue<Any>(defaultSharedPreferences) { key }

fun <V : Any> SharedPreferencesOwner.sharedPreferences(default: V) =
  SharedPrefValueWithDefault<SharedPreferencesOwner, V>(default, sharedPreferences) { property ->
    "${javaClass.canonicalName}_${property.name}"
  }

fun SharedPreferencesOwner.sharedPreferences() =
  SharedPrefStringValue<SharedPreferencesOwner>(sharedPreferences) { property ->
    "${javaClass.canonicalName}_${property.name}"
  }

interface SharedPreferencesOwner {
  val sharedPreferences: SharedPreferences get() = defaultSharedPreferences
}

class SharedPrefStringValue<T>(
  private val sharedPreferences: SharedPreferences,
  private val getKey: (KProperty<*>) -> String
) : ReadWriteProperty<T, String?> {

  override fun setValue(thisRef: T, property: KProperty<*>, value: String?) =
    sharedPreferences.edit { putString(getKey(property), value) }

  override fun getValue(thisRef: T, property: KProperty<*>) =
    sharedPreferences.getString(getKey(property), null)
}

class SharedPrefValueWithDefault<T, V : Any>(
  private val default: V,
  private val sharedPreferences: SharedPreferences,
  private val getKey: (KProperty<*>) -> String
) : ReadWriteProperty<T, V> {

  override fun setValue(thisRef: T, property: KProperty<*>, value: V) =
    sharedPreferences.edit {
      val key = getKey(property)
      when (value) {
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        else -> illegalValueType(key, value)
      }
    }

  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: T, property: KProperty<*>): V =
    sharedPreferences.run {
      val key = getKey(property)
      when (default) {
        is Long -> getLong(key, default)
        is String -> getString(key, default)
        is Int -> getInt(key, default)
        is Boolean -> getBoolean(key, default)
        is Float -> getFloat(key, default)
        else -> illegalValueType(key, default)
      } as V
    }

  private fun illegalValueType(key: String, value: Any): Nothing =
    throw IllegalArgumentException("Illegal value type ${value.javaClass.canonicalName} for key \"$key\"")
}