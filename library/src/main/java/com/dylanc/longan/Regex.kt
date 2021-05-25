@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.util.Patterns

/**
 * @author Dylan Cai
 */

inline fun String.isPhone() = Patterns.PHONE.matcher(this).matches()

inline fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()