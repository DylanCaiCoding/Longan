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

fun SharedPreferences.clear() = edit { clear() }

interface SharedPreferencesOwner {
  val sharedPreferences: SharedPreferences get() = defaultSharedPreferences
}

fun sharedPreferences(key: String, default: Int) =
  defaultSharedPreferences.property<Nothing?, Int>(default, SharedPreferences::getInt, SharedPreferences.Editor::putInt) { key }

fun Any.sharedPreferences(key: String, default: Int) =
  defaultSharedPreferences.property<Any, Int>(default, SharedPreferences::getInt, SharedPreferences.Editor::putInt) { key }

fun SharedPreferencesOwner.sharedPreferences(default: Int) =
  defaultSharedPreferences.property<SharedPreferencesOwner, Int>(default, SharedPreferences::getInt, SharedPreferences.Editor::putInt, key)

fun sharedPreferences(key: String, default: Long) =
  defaultSharedPreferences.property<Nothing?, Long>(default, SharedPreferences::getLong, SharedPreferences.Editor::putLong) { key }

fun Any.sharedPreferences(key: String, default: Long) =
  defaultSharedPreferences.property<Any, Long>(default, SharedPreferences::getLong, SharedPreferences.Editor::putLong) { key }

fun SharedPreferencesOwner.sharedPreferences(default: Long) =
  defaultSharedPreferences.property<SharedPreferencesOwner, Long>(default, SharedPreferences::getLong, SharedPreferences.Editor::putLong, key)

fun sharedPreferences(key: String, default: Float) =
  defaultSharedPreferences.property<Nothing?, Float>(default, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat) { key }

fun Any.sharedPreferences(key: String, default: Float) =
  defaultSharedPreferences.property<Any, Float>(default, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat) { key }

fun SharedPreferencesOwner.sharedPreferences(default: Float) =
  defaultSharedPreferences.property<SharedPreferencesOwner, Float>(default, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat, key)

fun sharedPreferences(key: String, default: Boolean) =
  defaultSharedPreferences.property<Nothing?, Boolean>(default, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean) { key }

fun Any.sharedPreferences(key: String, default: Boolean) =
  defaultSharedPreferences.property<Any, Boolean>(default, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean) { key }

fun SharedPreferencesOwner.sharedPreferences(default: Boolean) =
  defaultSharedPreferences.property<SharedPreferencesOwner, Boolean>(default, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean, key)

fun sharedPreferences(key: String) =
  defaultSharedPreferences.stringProperty<Nothing?> { key }

fun Any.sharedPreferences(key: String) =
  defaultSharedPreferences.stringProperty<Any> { key }

fun SharedPreferencesOwner.sharedPreferences() =
  defaultSharedPreferences.stringProperty<SharedPreferencesOwner>(key)

fun sharedPreferences(key: String, default: String) =
  defaultSharedPreferences.nullableProperty<Nothing?, String>(default, SharedPreferences::getString, SharedPreferences.Editor::putString) { key }

fun Any.sharedPreferences(key: String, default: String) =
  defaultSharedPreferences.nullableProperty<Any, String>(default, SharedPreferences::getString, SharedPreferences.Editor::putString) { key }

fun SharedPreferencesOwner.sharedPreferences(default: String) =
  defaultSharedPreferences.nullableProperty<SharedPreferencesOwner, String>(default, SharedPreferences::getString, SharedPreferences.Editor::putString, key)

fun sharedPreferences(key: String, default: Set<String>) =
  defaultSharedPreferences.nullableProperty<Nothing?, Set<String>>(default, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet) { key }

fun Any.sharedPreferences(key: String, default: Set<String>) =
  defaultSharedPreferences.nullableProperty<Any, Set<String>>(default, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet) { key }

fun SharedPreferencesOwner.sharedPreferences(default: Set<String>) =
  defaultSharedPreferences.nullableProperty<SharedPreferencesOwner, Set<String>>(default, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet, key)

private val SharedPreferencesOwner.key: (KProperty<*>) -> String
  get() = { "${javaClass.canonicalName}_${it.name}" }

private inline fun <T, V> SharedPreferences.property(
  defaultValue: V,
  crossinline getValue: SharedPreferences.(String, V) -> V,
  crossinline putValue: SharedPreferences.Editor.(String, V) -> SharedPreferences.Editor,
  crossinline getKey: (KProperty<*>) -> String
) = object : ReadWriteProperty<T, V> {
  override fun getValue(thisRef: T, property: KProperty<*>): V =
    getValue(getKey(property), defaultValue)

  override fun setValue(thisRef: T, property: KProperty<*>, value: V) =
    edit { putValue(getKey(property), value) }
}

private inline fun <T, V> SharedPreferences.nullableProperty(
  defaultValue: V,
  crossinline getValue: SharedPreferences.(String, V?) -> V?,
  crossinline putValue: SharedPreferences.Editor.(String, V?) -> SharedPreferences.Editor,
  crossinline getKey: (KProperty<*>) -> String
) = object : ReadWriteProperty<T, V> {
  override fun getValue(thisRef: T, property: KProperty<*>): V =
    getValue(getKey(property), null) ?: defaultValue

  override fun setValue(thisRef: T, property: KProperty<*>, value: V) =
    edit { putValue(getKey(property), value) }
}

private inline fun <T> SharedPreferences.stringProperty(
  crossinline getKey: (KProperty<*>) -> String
) = object : ReadWriteProperty<T, String?> {
  override fun getValue(thisRef: T, property: KProperty<*>): String? =
    getString(getKey(property), null)

  override fun setValue(thisRef: T, property: KProperty<*>, value: String?) =
    edit { putString(getKey(property), value) }
}