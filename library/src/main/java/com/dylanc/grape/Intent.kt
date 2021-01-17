@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
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