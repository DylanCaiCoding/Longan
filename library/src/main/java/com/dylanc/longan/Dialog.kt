@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.DeprecationLevel.ERROR

inline fun Context.alertDialog(block: AlertBuilder<*>.() -> Unit) =
  MaterialAlertBuilder(this).apply(block)

inline fun Fragment.alertDialog(block: AlertBuilder<*>.() -> Unit) =
  requireContext().alertDialog(block)

inline fun AlertBuilder<*>.okButton(noinline onClicked: (dialog: DialogInterface) -> Unit) =
  positiveButton(android.R.string.ok, onClicked)

inline fun AlertBuilder<*>.cancelButton(noinline onClicked: (dialog: DialogInterface) -> Unit) =
  negativeButton(android.R.string.cancel, onClicked)

interface AlertBuilder<out D : DialogInterface> {
  val context: Context

  var title: CharSequence
    @Deprecated(NO_GETTER, level = ERROR) get
  var titleResource: Int
    @Deprecated(NO_GETTER, level = ERROR) get

  var message: CharSequence
    @Deprecated(NO_GETTER, level = ERROR) get
  var messageResource: Int
    @Deprecated(NO_GETTER, level = ERROR) get

  var icon: Drawable
    @Deprecated(NO_GETTER, level = ERROR) get
  @setparam:DrawableRes
  var iconResource: Int
    @Deprecated(NO_GETTER, level = ERROR) get

  var customTitle: View
    @Deprecated(NO_GETTER, level = ERROR) get

  var customView: View
    @Deprecated(NO_GETTER, level = ERROR) get

  var isCancelable: Boolean
    @Deprecated(NO_GETTER, level = ERROR) get

  fun onCancelled(handler: (dialog: DialogInterface) -> Unit)

  fun onKeyPressed(handler: (dialog: DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean)

  fun positiveButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun positiveButton(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun negativeButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun negativeButton(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun neutralPressed(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun neutralPressed(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun items(items: List<CharSequence>, onItemSelected: (dialog: DialogInterface, index: Int) -> Unit)
  fun <T> items(items: List<T>, onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit)

  fun build(): D
  fun show(): D
}

class MaterialAlertBuilder(override val context: Context) : AlertBuilder<AlertDialog> {

  private val builder = MaterialAlertDialogBuilder(context)

  override var title: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setTitle(value)
    }

  override var titleResource: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setTitle(value)
    }

  override var message: CharSequence
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setMessage(value)
    }

  override var messageResource: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setMessage(value)
    }

  override var icon: Drawable
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setIcon(value)
    }

  override var iconResource: Int
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setIcon(value)
    }

  override var customTitle: View
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setCustomTitle(value)
    }

  override var customView: View
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setView(value)
    }

  override var isCancelable: Boolean
    @Deprecated(NO_GETTER, level = ERROR)
    get() = noGetter()
    set(value) {
      builder.setCancelable(value)
    }

  override fun onCancelled(handler: (dialog: DialogInterface) -> Unit) {
    builder.setOnCancelListener(handler)
  }

  override fun onKeyPressed(handler: (dialog: DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean) {
    builder.setOnKeyListener(handler)
  }

  override fun positiveButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setPositiveButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun positiveButton(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setPositiveButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun negativeButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setNegativeButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun negativeButton(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setNegativeButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun neutralPressed(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setNeutralButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun neutralPressed(buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit) {
    builder.setNeutralButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun items(items: List<CharSequence>, onItemSelected: (dialog: DialogInterface, index: Int) -> Unit) {
    builder.setItems(Array(items.size) { i -> items[i].toString() }) { dialog, which ->
      onItemSelected(dialog, which)
    }
  }

  override fun <T> items(items: List<T>, onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit) {
    builder.setItems(Array(items.size) { i -> items[i].toString() }) { dialog, which ->
      onItemSelected(dialog, items[which], which)
    }
  }

  override fun build(): AlertDialog = builder.create()

  override fun show(): AlertDialog = builder.show()
}