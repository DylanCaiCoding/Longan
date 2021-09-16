@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan.design

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.DeprecationLevel.ERROR

typealias AlertBuilderFactory<D> = (Context) -> AlertBuilder<D>

val Material: AlertBuilderFactory<DialogInterface> = { context ->
  object : AlertDialogBuilder() {
    override val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(context)
  }
}

val AppCompat: AlertBuilderFactory<DialogInterface> = { context ->
  object : AlertDialogBuilder() {
    override val builder: AlertDialog.Builder = AlertDialog.Builder(context)
  }
}

private var defaultAlertBuilderFactory: AlertBuilderFactory<*> = Material

fun initAlertBuilderFactory(factory: AlertBuilderFactory<*>) {
  defaultAlertBuilderFactory = factory
}

fun Fragment.alert(
  message: CharSequence,
  title: CharSequence? = null,
  block: (AlertBuilder<*>.() -> Unit)? = null
) =
  alert(defaultAlertBuilderFactory, message, title, block)

fun Context.alert(
  message: CharSequence,
  title: CharSequence? = null,
  block: (AlertBuilder<*>.() -> Unit)? = null
) =
  alert(defaultAlertBuilderFactory, message, title, block)

inline fun <D : DialogInterface> Fragment.alert(
  factory: AlertBuilderFactory<D>,
  message: CharSequence,
  title: CharSequence? = null,
  noinline block: (AlertBuilder<D>.() -> Unit)? = null
) =
  requireContext().alert(factory, message, title, block)

inline fun <D : DialogInterface> Context.alert(
  factory: AlertBuilderFactory<D>,
  message: CharSequence,
  title: CharSequence? = null,
  noinline block: (AlertBuilder<D>.() -> Unit)? = null
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    this.message = message
    block?.invoke(this)
  }.show()

fun Fragment.selector(
  items: List<CharSequence>,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int) -> Unit
) =
  selector(defaultAlertBuilderFactory, items, title, onItemSelected)

fun Context.selector(
  items: List<CharSequence>,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int) -> Unit
) =
  selector(defaultAlertBuilderFactory, items, title, onItemSelected)

inline fun <D : DialogInterface> Fragment.selector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int) -> Unit
) =
  requireContext().selector(factory, items, title, onItemSelected)

inline fun <D : DialogInterface> Context.selector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    items(items, onItemSelected)
  }.show()

fun <T> Fragment.selector(
  items: List<T>,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  selector(defaultAlertBuilderFactory, items, title, onItemSelected)

fun <T> Context.selector(
  items: List<T>,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  selector(defaultAlertBuilderFactory, items, title, onItemSelected)

inline fun <D : DialogInterface, T> Fragment.selector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  requireContext().selector(factory, items, title, onItemSelected)

inline fun <D : DialogInterface, T> Context.selector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    items(items, onItemSelected)
  }.show()

fun Fragment.singleChoiceSelector(
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int) -> Unit
) =
  singleChoiceSelector(defaultAlertBuilderFactory, items, checkIndex, title, onItemSelected)

fun Context.singleChoiceSelector(
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int) -> Unit
) =
  singleChoiceSelector(defaultAlertBuilderFactory, items, checkIndex, title, onItemSelected)

inline fun <D : DialogInterface> Fragment.singleChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int) -> Unit
) =
  requireContext().singleChoiceSelector(factory, items, checkIndex, title, onItemSelected)

inline fun <D : DialogInterface> Context.singleChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    singleChoiceItems(items, checkIndex, onItemSelected)
  }.show()

fun <T> Fragment.singleChoiceSelector(
  items: List<T>,
  checkIndex: Int,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  singleChoiceSelector(defaultAlertBuilderFactory, items, checkIndex, title, onItemSelected)

fun <T> Context.singleChoiceSelector(
  items: List<T>,
  checkIndex: Int,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  singleChoiceSelector(defaultAlertBuilderFactory, items, checkIndex, title, onItemSelected)

inline fun <D : DialogInterface, T> Fragment.singleChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  requireContext().singleChoiceSelector(factory, items, checkIndex, title, onItemSelected)

inline fun <D : DialogInterface, T> Context.singleChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    singleChoiceItems(items, checkIndex, onItemSelected)
  }.show()

fun Fragment.multiChoiceSelector(
  items: List<CharSequence>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int, Boolean) -> Unit
) =
  multiChoiceSelector(defaultAlertBuilderFactory, items, checkItems, title, onItemSelected)

fun Context.multiChoiceSelector(
  items: List<CharSequence>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, Int, Boolean) -> Unit
) =
  multiChoiceSelector(defaultAlertBuilderFactory, items, checkItems, title, onItemSelected)

inline fun <D : DialogInterface> Fragment.multiChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int, Boolean) -> Unit
) =
  requireContext().multiChoiceSelector(factory, items, checkItems, title, onItemSelected)

inline fun <D : DialogInterface> Context.multiChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<CharSequence>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, Int, Boolean) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    multiChoiceItems(items, checkItems, onItemSelected)
  }.show()

fun <T> Fragment.multiChoiceSelector(
  items: List<T>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  multiChoiceSelector(defaultAlertBuilderFactory, items, checkItems, title, onItemSelected)

fun <T> Context.multiChoiceSelector(
  items: List<T>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  multiChoiceSelector(defaultAlertBuilderFactory, items, checkItems, title, onItemSelected)

inline fun <D : DialogInterface, T> Fragment.multiChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  requireContext().multiChoiceSelector(factory, items, checkItems, title, onItemSelected)

inline fun <D : DialogInterface, T> Context.multiChoiceSelector(
  factory: AlertBuilderFactory<D>,
  items: List<T>,
  checkItems: BooleanArray,
  title: CharSequence? = null,
  noinline onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  alertDialog(factory) {
    title?.let { this.title = it }
    multiChoiceItems(items, checkItems, onItemSelected)
  }.show()

fun Context.alertDialog(block: AlertBuilder<*>.() -> Unit) =
  alertDialog(defaultAlertBuilderFactory, block)

inline fun <D : DialogInterface> Context.alertDialog(
  factory: AlertBuilderFactory<D>,
  block: AlertBuilder<D>.() -> Unit
) =
  factory(this).apply(block)

inline fun AlertBuilder<*>.okButton(noinline onClicked: (dialog: DialogInterface) -> Unit) =
  positiveButton(android.R.string.ok, onClicked)

inline fun AlertBuilder<*>.cancelButton(noinline onClicked: (dialog: DialogInterface) -> Unit) =
  negativeButton(android.R.string.cancel, onClicked)

inline fun <T> AlertBuilder<*>.items(
  items: List<T>,
  crossinline onItemSelected: (dialog: DialogInterface, item: T, index: Int) -> Unit
) =
  items(items.map { it.toString() }) { dialog, which ->
    onItemSelected(dialog, items[which], which)
  }

inline fun <T> AlertBuilder<*>.singleChoiceItems(
  items: List<T>,
  checkIndex: Int,
  crossinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  singleChoiceItems(items.map { it.toString() }, checkIndex) { dialog, which ->
    onItemSelected(dialog, items[which], which)
  }

inline fun AlertBuilder<*>.singleChoiceItems(
  items: List<CharSequence>,
  checkItem: CharSequence,
  crossinline onItemSelected: (DialogInterface, Int) -> Unit
) =
  singleChoiceItems(items.map { it.toString() }, items.indexOfFirst { it == checkItem }) { dialog, which ->
    onItemSelected(dialog, which)
  }

inline fun <T> AlertBuilder<*>.singleChoiceItems(
  items: List<T>,
  checkItem: T,
  crossinline onItemSelected: (DialogInterface, T, Int) -> Unit
) =
  singleChoiceItems(items.map { it.toString() }, items.indexOfFirst { it == checkItem }) { dialog, which ->
    onItemSelected(dialog, items[which], which)
  }

inline fun <T> AlertBuilder<*>.multiChoiceItems(
  items: List<T>,
  checkItems: BooleanArray,
  crossinline onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  multiChoiceItems(items.map { it.toString() }, checkItems) { dialog, which, isChecked ->
    onItemSelected(dialog, items[which], which, isChecked)
  }

inline fun Dialog.doOnCancel(noinline block: (DialogInterface) -> Unit) = apply {
  setOnCancelListener(block)
}

inline fun Dialog.doOnDismiss(noinline block: (DialogInterface) -> Unit) = apply {
  setOnDismissListener(block)
}

inline fun Dialog.doOnShow(noinline block: (DialogInterface) -> Unit) = apply {
  setOnShowListener(block)
}

inline fun DialogInterface.doOnCancel(noinline block: (DialogInterface) -> Unit) = apply {
  check(this is Dialog)
  setOnCancelListener(block)
}

inline fun DialogInterface.doOnDismiss(noinline block: (DialogInterface) -> Unit) = apply {
  check(this is Dialog)
  setOnDismissListener(block)
}

inline fun DialogInterface.doOnShow(noinline block: (DialogInterface) -> Unit) = apply {
  check(this is Dialog)
  setOnShowListener(block)
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

  fun singleChoiceItems(
    items: List<CharSequence>,
    checkedIndex: Int,
    onItemSelected: (dialog: DialogInterface, index: Int) -> Unit
  )

  fun multiChoiceItems(
    items: List<CharSequence>,
    checkedItems: BooleanArray,
    onItemSelected: (dialog: DialogInterface, index: Int, isChecked: Boolean) -> Unit
  )

  fun build(): D
  fun show(): D
}

abstract class AlertDialogBuilder : AlertBuilder<AlertDialog> {

  abstract val builder: AlertDialog.Builder
  override val context get() = builder.context

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

  override fun singleChoiceItems(items: List<CharSequence>, checkedIndex: Int, onItemSelected: (DialogInterface, Int) -> Unit) {
    builder.setSingleChoiceItems(items.toTypedArray(), checkedIndex) { dialog, which ->
      onItemSelected(dialog, which)
    }
  }

  override fun multiChoiceItems(
    items: List<CharSequence>,
    checkedItems: BooleanArray,
    onItemSelected: (DialogInterface, Int, Boolean) -> Unit
  ) {
    builder.setMultiChoiceItems(items.toTypedArray(), checkedItems) { dialog, which, isChecked ->
      onItemSelected(dialog, which, isChecked)
    }
  }

  override fun build(): AlertDialog = builder.create()

  override fun show(): AlertDialog = builder.show()
}
