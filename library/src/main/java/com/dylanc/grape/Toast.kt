@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import android.widget.Toast

/**
 * @author Dylan Cai
 */

fun Context.toast(text: String) {
  Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(text: String) {
  Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun toast(text: String) {
  Toast.makeText(application, text, Toast.LENGTH_SHORT).show()
}

fun longToast(text: String) {
  Toast.makeText(application, text, Toast.LENGTH_LONG).show()
}