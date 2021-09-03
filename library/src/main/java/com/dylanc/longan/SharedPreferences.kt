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

private var defaultSharedPreferences = sharedPreferencesOf()

fun initSharedPreferences(name: String, mode: Int = Context.MODE_PRIVATE) {
  defaultSharedPreferences = sharedPreferencesOf(name, mode)
}

fun sharedPreferencesOf(
  name: String = "${packageName}_preferences",
  mode: Int = Context.MODE_PRIVATE
): SharedPreferences =
  application.getSharedPreferences(name, mode)

fun SharedPreferences.clear() = edit { clear() }

interface SharedPreferencesOwner {
  val sharedPreferences: SharedPreferences get() = defaultSharedPreferences
}

fun SharedPreferencesOwner.sharedPreferences(default: Int) =
  sharedPreferences.property(default, SharedPreferences::getInt, SharedPreferences.Editor::putInt)

fun SharedPreferencesOwner.sharedPreferences(default: Long) =
  sharedPreferences.property(default, SharedPreferences::getLong, SharedPreferences.Editor::putLong)

fun SharedPreferencesOwner.sharedPreferences(default: Float) =
  sharedPreferences.property(default, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat)

fun SharedPreferencesOwner.sharedPreferences(default: Boolean) =
  sharedPreferences.property(default, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)

fun SharedPreferencesOwner.sharedPreferences(default: String) =
  sharedPreferences.nullableProperty(default, SharedPreferences::getString, SharedPreferences.Editor::putString)

fun SharedPreferencesOwner.sharedPreferences(default: Set<String>) =
  sharedPreferences.nullableProperty(default, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet)

fun SharedPreferencesOwner.sharedPreferences() =
  object : ReadWriteProperty<SharedPreferencesOwner, String?> {
    override fun getValue(thisRef: SharedPreferencesOwner, property: KProperty<*>): String? =
      sharedPreferences.getString(getKey(property), null)

    override fun setValue(thisRef: SharedPreferencesOwner, property: KProperty<*>, value: String?) =
      sharedPreferences.edit { putString(getKey(property), value) }
  }

private inline fun <V> SharedPreferences.property(
  defaultValue: V,
  crossinline getValue: SharedPreferences.(String, V) -> V,
  crossinline putValue: SharedPreferences.Editor.(String, V) -> SharedPreferences.Editor
) = object : ReadWriteProperty<SharedPreferencesOwner, V> {
  override fun getValue(thisRef: SharedPreferencesOwner, property: KProperty<*>): V =
    getValue(thisRef.getKey(property), defaultValue)

  override fun setValue(thisRef: SharedPreferencesOwner, property: KProperty<*>, value: V) =
    edit { putValue(thisRef.getKey(property), value) }
}

private inline fun <V> SharedPreferences.nullableProperty(
  defaultValue: V,
  crossinline getValue: SharedPreferences.(String, V?) -> V?,
  crossinline putValue: SharedPreferences.Editor.(String, V?) -> SharedPreferences.Editor
) = object : ReadWriteProperty<SharedPreferencesOwner, V> {
  override fun getValue(thisRef: SharedPreferencesOwner, property: KProperty<*>): V =
    getValue(thisRef.getKey(property), null) ?: defaultValue

  override fun setValue(thisRef: SharedPreferencesOwner, property: KProperty<*>, value: V) =
    edit { putValue(thisRef.getKey(property), value) }
}

private fun SharedPreferencesOwner.getKey(property: KProperty<*>) =
  "${javaClass.canonicalName}_${property.name}"
