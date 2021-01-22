@file:Suppress("unused")

package com.dylanc.grape

import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author Dylan Cai
 */

fun toast(text: String, block: Toast.() -> Unit = {}) =
  Toast.makeText(application, text, Toast.LENGTH_SHORT).apply(block).show()

fun toast(@StringRes resId: Int, block: Toast.() -> Unit = {}) =
  Toast.makeText(application, resId, Toast.LENGTH_SHORT).apply(block).show()

fun longToast(text: String, block: Toast.() -> Unit = {}) =
  Toast.makeText(application, text, Toast.LENGTH_LONG).apply(block).show()

fun longToast(@StringRes resId: Int, block: Toast.() -> Unit = {}) =
  Toast.makeText(application, resId, Toast.LENGTH_LONG).apply(block).show()