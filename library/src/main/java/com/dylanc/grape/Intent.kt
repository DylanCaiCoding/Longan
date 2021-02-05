@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import java.io.Serializable

/**
 * @author Dylan Cai
 */

inline fun <reified T : Any> Context.intentOf(bundle: Bundle) =
  Intent(this, T::class.java).apply { putExtras(bundle) }

inline fun <reified T : Any> Context.intentOf(vararg pairs: Pair<String, *>) =
  Intent(this, T::class.java).apply { putExtras(bundleOf(*pairs)) }

inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

inline fun Intent.newDocument(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT) }

inline fun Intent.excludeFromRecents(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

inline fun <reified T> Activity.intentExtra(name: String) = lazy {
  getIntentExtra(T::class.java, name)
}

inline fun <reified T> Activity.intentExtra(name: String, defaultValue: T) = lazy {
  getIntentExtra(T::class.java, name, defaultValue)
}

inline fun <reified T> Activity.safeIntentExtra(name: String) = lazy {
  checkNotNull(getIntentExtra(T::class.java, name)) { "No intent value for key \"$name\"" }
}

@Suppress("UNCHECKED_CAST")
fun <T> Activity.getIntentExtra(clazz: Class<T>, name: String, defaultValue: T) =
  when (clazz) {
    Boolean::class.java -> intent.getBooleanExtra(name, defaultValue as Boolean)
    Byte::class.java -> intent.getByteExtra(name, defaultValue as Byte)
    Char::class.java -> intent.getCharExtra(name, defaultValue as Char)
    Double::class.java -> intent.getDoubleExtra(name, defaultValue as Double)
    Float::class.java -> intent.getFloatExtra(name, defaultValue as Float)
    Int::class.java -> intent.getIntExtra(name, defaultValue as Int)
    Long::class.java -> intent.getLongExtra(name, defaultValue as Long)
    Short::class.java -> intent.getShortExtra(name, defaultValue as Short)
    String::class.java -> intent.getStringExtra(name) ?: defaultValue
    else -> {
      val valueType = clazz.canonicalName
      throw IllegalArgumentException("Illegal value type $valueType for key \"$name\"")
    }
  } as T

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
fun <T> Activity.getIntentExtra(clazz: Class<T>, name: String) =
  when (clazz) {
    Boolean::class.java, Byte::class.java, Char::class.java, Double::class.java,
    Float::class.java, Int::class.java, Long::class.java, Short::class.java -> {
      val valueType = clazz.componentType.canonicalName
      throw IllegalArgumentException(
        "Please set default value for type $valueType"
      )
    }

    Bundle::class.java -> intent.getBundleExtra(name)
    CharSequence::class.java -> intent.getCharSequenceExtra(name)
    String::class.java -> intent.getStringExtra(name)
    Parcelable::class.java -> intent.getParcelableExtra(name)

    BooleanArray::class.java -> intent.getBooleanArrayExtra(name)
    ByteArray::class.java -> intent.getByteArrayExtra(name)
    CharArray::class.java -> intent.getCharArrayExtra(name)
    DoubleArray::class.java -> intent.getStringArrayExtra(name)
    FloatArray::class.java -> intent.getFloatArrayExtra(name)
    IntArray::class.java -> intent.getIntArrayExtra(name)
    LongArray::class.java -> intent.getLongArrayExtra(name)
    ShortArray::class.java -> intent.getShortArrayExtra(name)

    else -> {
      when {
        clazz.isArray -> {
          val componentType = clazz.componentType
          @Suppress("UNCHECKED_CAST")
          when (componentType) {
            Parcelable::class.java -> intent.getParcelableArrayExtra(name)
            String::class.java -> intent.getStringArrayExtra(name)
            CharSequence::class.java -> intent.getCharSequenceArrayExtra(name)
            Serializable::class.java -> intent.getSerializableExtra(name)
            else -> {
              val valueType = componentType.canonicalName
              throw IllegalArgumentException(
                "Illegal value array type $valueType for key \"$name\""
              )
            }
          }
        }
        else -> {
          val valueType = clazz.canonicalName
          throw IllegalArgumentException("Illegal value type $valueType for key \"$name\"")
        }
      }

    }
  } as T?
