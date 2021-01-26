@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Dylan Cai
 */

var defaultSharedPreferences = sharedPreferencesOf()
val defaultSharedPreferencesOwner by lazy { object : SharedPreferencesOwner {} }

fun sharedPreferencesOf(name: String = packageName, mode: Int = Context.MODE_PRIVATE): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun <T : Any> Any.sharedPreferences(key: String? = null, default: T) =
  SharedPreferencesValueWithDefault(key, default, sharedPreferencesOwner.sharedPreferences)

fun Any.sharedPreferences(key: String? = null) =
  SharedPreferencesStringValue(key, sharedPreferencesOwner.sharedPreferences)

private val Any.sharedPreferencesOwner: SharedPreferencesOwner
  get() = if (this is SharedPreferencesOwner) this else defaultSharedPreferencesOwner

fun <T : Any> SharedPreferences.put(key: String, value: T) {
  edit().apply {
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
  }.apply()
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> SharedPreferences.getOrDefault(key: String, default: T) =
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

class SharedPreferencesStringValue(
  private val key: String? = null,
  private val sharedPreferences: SharedPreferences
) : ReadWriteProperty<SharedPreferencesOwner, String?> {

  override fun setValue(thisRef: SharedPreferencesOwner, property: KProperty<*>, value: String?) {
    sharedPreferences.edit().putString(key ?: property.name, value).apply()
  }

  override fun getValue(thisRef: SharedPreferencesOwner, property: KProperty<*>): String? {
    return sharedPreferences.getString(key ?: property.name, null)
  }
}

class SharedPreferencesValueWithDefault<T : Any>(
  private val key: String? = null,
  private val default: T,
  private val sharedPreferences: SharedPreferences
) : ReadWriteProperty<Any, T> {

  override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
    sharedPreferences.put(key ?: property.name, value)
  }
  override fun getValue(thisRef: Any, property: KProperty<*>): T {
    return sharedPreferences.getOrDefault(key ?: property.name, default)
  }
}
