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

inline fun Intent.newDocument(): Intent =
  apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    } else {
      @Suppress("DEPRECATION")
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
    }
  }

inline fun Intent.excludeFromRecents(): Intent =
  apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

inline fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

inline fun Activity.intentBooleanExtra(name: String, defaultValue: Boolean) = lazy { intent.getBooleanExtra(name, defaultValue) }

inline fun Activity.intentByteExtra(name: String, defaultValue: Byte) = lazy { intent.getByteExtra(name, defaultValue) }

inline fun Activity.intentCharExtra(name: String, defaultValue: Char) = lazy { intent.getCharExtra(name, defaultValue) }

inline fun Activity.intentDoubleExtra(name: String, defaultValue: Double) = lazy { intent.getDoubleExtra(name, defaultValue) }

inline fun Activity.intentFloatExtra(name: String, defaultValue: Float) = lazy { intent.getFloatExtra(name, defaultValue) }

inline fun Activity.intentIntExtra(name: String, defaultValue: Int) = lazy { intent.getIntExtra(name, defaultValue) }

inline fun Activity.intentLongExtra(name: String, defaultValue: Long) = lazy { intent.getLongExtra(name, defaultValue) }

inline fun Activity.intentShortExtra(name: String, defaultValue: Short) = lazy { intent.getShortExtra(name, defaultValue) }

inline fun Activity.intentStringExtra(name: String) = lazy { intent.getStringExtra(name) }

inline fun Activity.intentCharSequenceExtra(name: String) = lazy { intent.getCharSequenceExtra(name) }

inline fun <T : Parcelable> Activity.intentParcelableExtra(name: String) = lazy { intent.getParcelableExtra<T?>(name) }

@Suppress("UNCHECKED_CAST")
inline fun <T : Serializable> Activity.intentSerializableExtra(name: String) = lazy { intent.getSerializableExtra(name) as T? }

inline fun Activity.intentBooleanArrayExtra(name: String) = lazy { intent.getBooleanArrayExtra(name) }

inline fun Activity.intentByteArrayExtra(name: String) = lazy { intent.getByteArrayExtra(name) }

inline fun Activity.intentCharArrayExtra(name: String) = lazy { intent.getCharArrayExtra(name) }

inline fun Activity.intentDoubleArrayExtra(name: String) = lazy { intent.getDoubleArrayExtra(name) }

inline fun Activity.intentFloatArrayExtra(name: String) = lazy { intent.getFloatArrayExtra(name) }

inline fun Activity.intentIntArrayExtra(name: String) = lazy { intent.getIntArrayExtra(name) }

inline fun Activity.intentLongArrayExtra(name: String) = lazy { intent.getLongArrayExtra(name) }

inline fun Activity.intentShortArrayExtra(name: String) = lazy { intent.getShortArrayExtra(name) }

inline fun Activity.intentStringArrayExtra(name: String) = lazy { intent.getStringArrayExtra(name) }

inline fun Activity.intentCharSequenceArrayExtra(name: String) = lazy { intent.getCharSequenceArrayExtra(name) }

inline fun Activity.intentParcelableArrayExtra(name: String) = lazy { intent.getParcelableArrayExtra(name) }

inline fun Activity.intentBundleExtra(name: String) = lazy { intent.getBundleExtra(name) }

inline fun <reified T> Activity.intent(name: String) = intent(T::class.java, name)

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
fun <T> Activity.intent(clazz: Class<T>, name: String) = lazy {
  when (clazz) {
    Boolean::class.java -> intent.getBooleanExtra(name, false)
    Byte::class.java -> intent.getByteExtra(name, -1)
//    Char::class.java -> intent.getCharExtra(name,)
    Double::class.java -> intent.getStringExtra(name)
    Float::class.java -> intent.getFloatExtra(name, -1f)
    Int::class.java -> intent.getIntExtra(name, -1)
    Long::class.java -> intent.getLongExtra(name, -1)
    Short::class.java -> intent.getShortExtra(name, -1)

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

//    List::class.java-> intent.getlis
    else -> {
      when {
        clazz.isArray -> {
          val componentType = clazz.componentType
          @Suppress("UNCHECKED_CAST") // Checked by reflection.
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

//        clazz == List::class.java -> {
//          val componentType = clazz.componentType
//          @Suppress("UNCHECKED_CAST") // Checked by reflection.
//          when (componentType) {
////            Parcelable::class.java -> intent.getParcelableArrayExtra(name)
//            String::class.java -> intent.getStringArrayListExtra(name)
//            CharSequence::class.java -> intent.getCharSequenceArrayListExtra(name)
//            Serializable::class.java -> intent.getSerializableExtra(name)
//            else -> {
//              val valueType = componentType.canonicalName
//              throw IllegalArgumentException(
//                "Illegal value array type $valueType for key \"$name\""
//              )
//            }
//          }
//        }

        else -> {
          val valueType = clazz.canonicalName
          throw IllegalArgumentException("Illegal value type $valueType for key \"$name\"")
        }
      }

    }
  } as T?
}