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

inline fun AlertBuilder<*>.items(
  vararg items: CharSequence,
  crossinline onItemSelected: (dialog: DialogInterface, item: CharSequence, index: Int) -> Unit
) =
  items(listOf(*items)) { dialog, which ->
    onItemSelected(dialog, items[which], which)
  }

inline fun <T> AlertBuilder<*>.items(
  vararg items: T,
  crossinline onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit
) =
  items(listOf(*items)) { dialog, item, which ->
    onItemSelected(dialog, item, which)
  }

inline fun AlertBuilder<*>.singleChoiceItems(
  vararg items: CharSequence,
  checkIndex: Int,
  crossinline onItemSelected: (dialog: DialogInterface, item: CharSequence, index: Int) -> Unit
) =
  singleChoiceItems(listOf(*items), checkIndex) { dialog, which ->
    onItemSelected(dialog, items[which], which)
  }

inline fun <T> AlertBuilder<*>.singleChoiceItems(
  vararg items: T,
  checkIndex: Int,
  crossinline onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit
) =
  singleChoiceItems(listOf(*items), checkIndex) { dialog, item, which ->
    onItemSelected(dialog, item, which)
  }

inline fun AlertBuilder<*>.multiChoiceItems(
  vararg items: CharSequence,
  checkItems: BooleanArray,
  crossinline onItemSelected: (dialog: DialogInterface, item: CharSequence, index: Int, Boolean) -> Unit
) =
  multiChoiceItems(listOf(*items), checkItems) { dialog, which, isChecked ->
    onItemSelected(dialog, items[which], which, isChecked)
  }

inline fun <T> AlertBuilder<*>.multiChoiceItems(
  vararg items: T,
  checkItems: BooleanArray,
  crossinline onItemSelected: (dialog: DialogInterface, item: T, index: Int, Boolean) -> Unit
) =
  multiChoiceItems(listOf(*items), checkItems) { dialog, item, which, isChecked ->
    onItemSelected(dialog, item, which, isChecked)
  }

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

  fun onCancelled(handler: (DialogInterface) -> Unit)

  fun onKeyPressed(handler: (DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean)

  fun positiveButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun positiveButton(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun negativeButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun negativeButton(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun neutralPressed(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)
  fun neutralPressed(@StringRes buttonTextResource: Int, onClicked: (dialog: DialogInterface) -> Unit)

  fun items(items: List<CharSequence>, onItemSelected: (dialog: DialogInterface, index: Int) -> Unit)
  fun <T> items(items: List<T>, onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit)

  fun singleChoiceItems(
    items: List<CharSequence>,
    checkIndex: Int, onItemSelected: (dialog: DialogInterface, index: Int) -> Unit
  )

  fun <T> singleChoiceItems(
    items: List<T>,
    checkIndex: Int,
    onItemSelected: (dialog: DialogInterface, items: T, index: Int) -> Unit
  )

  fun multiChoiceItems(
    items: List<CharSequence>,
    checkItems: BooleanArray,
    onItemSelected: (dialog: DialogInterface, index: Int, isChecked: Boolean) -> Unit
  )

  fun <T> multiChoiceItems(
    items: List<T>,
    checkItems: BooleanArray,
    onItemSelected: (dialog: DialogInterface, items: T, index: Int, Boolean) -> Unit
  )

  fun build(): D
  fun show(): D
}

class MaterialAlertBuilder(override val context: Context) : AlertBuilder<AlertDialog> {

  private val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(context)

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

  override fun onCancelled(handler: (DialogInterface) -> Unit) {
    builder.setOnCancelListener(handler)
  }

  override fun onKeyPressed(handler: (DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean) {
    builder.setOnKeyListener(handler)
  }

  override fun positiveButton(buttonText: String, onClicked: (DialogInterface) -> Unit) {
    builder.setPositiveButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun positiveButton(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
    builder.setPositiveButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun negativeButton(buttonText: String, onClicked: (DialogInterface) -> Unit) {
    builder.setNegativeButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun negativeButton(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
    builder.setNegativeButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun neutralPressed(buttonText: String, onClicked: (DialogInterface) -> Unit) {
    builder.setNeutralButton(buttonText) { dialog, _ -> onClicked(dialog) }
  }

  override fun neutralPressed(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
    builder.setNeutralButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
  }

  override fun items(items: List<CharSequence>, onItemSelected: (DialogInterface, Int) -> Unit) {
    builder.setItems(items.toTypedArray()) { dialog, which ->
      onItemSelected(dialog, which)
    }
  }

  override fun <T> items(items: List<T>, onItemSelected: (DialogInterface, T, Int) -> Unit) {
    builder.setItems(Array(items.size) { items[it].toString() }) { dialog, which ->
      onItemSelected(dialog, items[which], which)
    }
  }

  override fun singleChoiceItems(items: List<CharSequence>, checkIndex: Int, onItemSelected: (DialogInterface, Int) -> Unit) {
    builder.setSingleChoiceItems(items.toTypedArray(), checkIndex) { dialog, which ->
      onItemSelected(dialog, which)
    }
  }

  override fun <T> singleChoiceItems(items: List<T>, checkIndex: Int, onItemSelected: (DialogInterface, T, Int) -> Unit) {
    builder.setSingleChoiceItems(Array(items.size) { items[it].toString() }, checkIndex) { dialog, which ->
      onItemSelected(dialog, items[which], which)
    }
  }

  override fun multiChoiceItems(
    items: List<CharSequence>,
    checkItems: BooleanArray,
    onItemSelected: (DialogInterface, Int, Boolean) -> Unit
  ) {
    builder.setMultiChoiceItems(items.toTypedArray(), checkItems) { dialog, which, isChecked ->
      onItemSelected(dialog, which, isChecked)
    }
  }

  override fun <T> multiChoiceItems(
    items: List<T>,
    checkItems: BooleanArray,
    onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
  ) {
    builder.setMultiChoiceItems(Array(items.size) { items[it].toString() }, checkItems) { dialog, which, isChecked ->
      onItemSelected(dialog, items[which], which, isChecked)
    }
  }

  override fun build(): AlertDialog = builder.create()

  override fun show(): AlertDialog = builder.show()
}