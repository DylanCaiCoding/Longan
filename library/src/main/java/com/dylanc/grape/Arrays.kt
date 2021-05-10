@file:Suppress("unused")

package com.dylanc.grape

/**
 * @author Dylan Cai
 */

inline fun <T> Array<T>.percentage(predicate: (T) -> Boolean) =
  filter(predicate).size.toFloat() / size