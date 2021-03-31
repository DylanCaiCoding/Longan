@file:Suppress("unused")

package com.dylanc.grape

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout

/**
 * @author Dylan Cai
 */

inline fun Activity.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  window.decorView.snackbar(text, block)

inline fun Activity.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  window.decorView.snackbar(resId, block)

inline fun Fragment.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  requireView().snackbar(text, block)

inline fun Fragment.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  requireView().snackbar(resId, block)

inline fun View.snackbar(text: String, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, text, Snackbar.LENGTH_SHORT).apply(block).apply { show() }

inline fun View.snackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, resId, Snackbar.LENGTH_SHORT).apply(block).apply { show() }

inline fun Activity.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  window.decorView.longSnackbar(text, block)

inline fun Activity.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  window.decorView.longSnackbar(resId, block)

inline fun Fragment.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  requireView().longSnackbar(text, block)

inline fun Fragment.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  requireView().longSnackbar(resId, block)

inline fun View.longSnackbar(text: String, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, text, Snackbar.LENGTH_LONG).apply(block).apply { show() }

inline fun View.longSnackbar(@StringRes resId: Int, block: Snackbar.() -> Unit = {}) =
  Snackbar.make(this, resId, Snackbar.LENGTH_LONG).apply(block).apply { show() }

inline fun Snackbar.setCustomView(@LayoutRes layoutId: Int, onBindView: View.() -> Unit = {}) {
  val parent = view as Snackbar.SnackbarLayout
  val contentView = parent.getChildAt(0) as SnackbarContentLayout
  val inflater = LayoutInflater.from(view.context)
  val decorView = inflater.inflate(R.layout.design_layout_snackbar_include, contentView, false) as SnackbarContentLayout
  decorView.apply {
    addView(inflater.inflate(layoutId, contentView, false).apply(onBindView))
    id = contentView.id
    findViewById<TextView>(R.id.snackbar_text).apply {
      id = com.google.android.material.R.id.snackbar_text
      text = contentView.findViewById<TextView>(id).text.toString()
    }
    findViewById<View>(R.id.snackbar_action).apply {
      id = com.google.android.material.R.id.snackbar_action
    }
  }
  try {
    val method = decorView::class.java.getDeclaredMethod("onFinishInflate")
    method.isAccessible = true
    method.invoke(decorView)
  } catch (e: Exception) {
  }
  parent.apply {
    removeView(contentView)
    addView(decorView, indexOfChild(contentView))
  }
}