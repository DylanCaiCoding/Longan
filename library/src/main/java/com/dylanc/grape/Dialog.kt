@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

inline fun Context.materialDialog(block: MaterialAlertDialogBuilder.() -> Unit) =
  MaterialAlertDialogBuilder(this)
    .apply(block)
    .create()

inline fun Fragment.materialDialog(block: MaterialAlertDialogBuilder.() -> Unit) =
  requireActivity().materialDialog(block)