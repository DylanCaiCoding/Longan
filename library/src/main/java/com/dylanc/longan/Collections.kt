@file:Suppress("unused")

package com.dylanc.longan

/**
 * @author Dylan Cai
 */

inline fun <T> List<T>.percentage(predicate: (T) -> Boolean) =
  filter(predicate).size.toFloat() / size