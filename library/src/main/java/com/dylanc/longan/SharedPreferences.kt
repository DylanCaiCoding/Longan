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

fun sharedPreferencesOf(name: String = packageName, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun <T : Any> Any.sharedPreferences(key: String, default: T) =
  SharedPrefValueWithDefault(default, defaultSharedPreferences) { key }

fun Any.sharedPreferences(key: String) =
  SharedPrefStringValue(defaultSharedPreferences) { key }

fun <T : Any> SharedPreferencesOwner.sharedPreferences(default: T) =
  SharedPrefValueWithDefault(default, sharedPreferences) { property -> "${javaClass.canonicalName}_${property.name}" }

fun SharedPreferencesOwner.sharedPreferences() =
  SharedPrefStringValue(sharedPreferences) { property -> "${javaClass.canonicalName}_${property.name}" }

interface SharedPreferencesOwner {
  val sharedPreferences: SharedPreferences get() = defaultSharedPreferences
}

class SharedPrefStringValue(
  private val sharedPreferences: SharedPreferences,
  private val getKey: (KProperty<*>) -> String
) : ReadWriteProperty<Any, String?> {

  override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) =
    sharedPreferences.edit { putString(getKey(property), value) }

  override fun getValue(thisRef: Any, property: KProperty<*>) =
    sharedPreferences.getString(getKey(property), null)
}

class SharedPrefValueWithDefault<T : Any>(
  private val default: T,
  private val sharedPreferences: SharedPreferences,
  private val getKey: (KProperty<*>) -> String
) : ReadWriteProperty<Any, T> {

  override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
    sharedPreferences.edit {
      val key = getKey(property)
      when (value) {
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        else -> {
          val valueType = value.javaClass.canonicalName
          throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
        }
      }
    }

  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: Any, property: KProperty<*>): T =
    sharedPreferences.run {
      val key = getKey(property)
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
}