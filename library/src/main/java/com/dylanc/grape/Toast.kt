@file:Suppress("unused")

package com.dylanc.grape

import android.widget.Toast

/**
 * @author Dylan Cai
 */

fun toast(text: String?) {
  if (text.isNullOrBlank()) return
  Toast.makeText(application, text, Toast.LENGTH_SHORT).show()
}

fun longToast(text: String?) {
  if (text.isNullOrBlank()) return
  Toast.makeText(application, text, Toast.LENGTH_LONG).show()
}