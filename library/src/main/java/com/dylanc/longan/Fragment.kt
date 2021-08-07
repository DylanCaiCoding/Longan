@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

inline fun Fragment.withArguments(vararg pairs: Pair<String, *>) = apply {
  arguments = bundleOf(*pairs)
}

inline fun <reified T> Fragment.arguments(key: String) = lazy<T?> {
  arguments[key]
}

inline fun <reified T> Fragment.arguments(key: String, defaultValue: T) = lazy {
  arguments[key] ?: defaultValue
}

inline fun <reified T> Fragment.safeArguments(name: String) = lazy<T> {
  checkNotNull(arguments[name]) { "No intent value for key \"$name\"" }
}