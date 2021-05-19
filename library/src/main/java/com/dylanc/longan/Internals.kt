package com.dylanc.longan

/**
 * @author Dylan Cai
 */
object Internals {
  const val NO_GETTER: String = "Property does not have a getter"

  fun noGetterError(): Nothing = throw NotImplementedError(NO_GETTER)
}