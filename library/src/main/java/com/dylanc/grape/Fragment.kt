@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * @author Dylan Cai
 */

inline fun Fragment.withArguments(vararg pairs: Pair<String, *>) = apply {
  arguments = bundleOf(*pairs)
}

inline fun <reified T> Fragment.arguments(key: String): Lazy<T?> = lazy {
  arguments[key]
}

inline fun <reified T> Fragment.arguments(key: String, defaultValue: T): Lazy<T> = lazy {
  arguments[key] ?: defaultValue
}

inline fun <reified T> Fragment.safeArguments(name: String): Lazy<T> = lazy {
  checkNotNull(arguments[name]) { "No intent value for key \"$name\"" }
}

inline val Fragment.lifecycleOwner: LifecycleOwner get() = this