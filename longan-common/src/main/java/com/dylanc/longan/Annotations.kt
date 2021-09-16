@file:Suppress("unused")

package com.dylanc.longan

import kotlin.RequiresOptIn.Level.WARNING
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.*

/**
 * @author Dylan Cai
 */

@RequiresOptIn(level = WARNING)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY)
annotation class ExperimentalApi
