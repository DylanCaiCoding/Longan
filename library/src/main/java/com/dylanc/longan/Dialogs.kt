@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlin.DeprecationLevel.ERROR


inline fun Dialog.doOnCancel(noinline block: (DialogInterface) -> Unit) = apply {
  setOnCancelListener(block)
}

inline fun Dialog.doOnDismiss(noinline block: (DialogInterface) -> Unit) = apply {
  setOnDismissListener(block)
}

inline fun Dialog.doOnShow(noinline block: (DialogInterface) -> Unit) = apply {
  setOnShowListener(block)
}

inline fun Fragment.alert(
  message: CharSequence,
  title: CharSequence? = null,
  block: AlertBuilder<*>.() -> Unit
) =
  requireContext().alert(message, title, block)

inline fun Context.alert(
  message: CharSequence,
  title: CharSequence? = null,
  block: AlertBuilder<*>.() -> Unit
) =
  materialAlertDialog {
    title?.let { this.title = it }
    this.message = message
    apply(block)
  }.show()

inline fun Fragment.selector(
  items: List<CharSequence>,
  title: CharSequence? = null,
  noinline onClicked: (DialogInterface, Int) -> Unit
) =
  requireContext().selector(items, title, onClicked)

fun Context.selector(
  items: List<CharSequence>,
  title: CharSequence? = null,
  onClicked: (DialogInterface, Int) -> Unit
) =
  materialAlertDialog {
    title?.let { this.title = it }
    items(items, onClicked)
  }.show()

inline fun Fragment.singleChoiceSelector(
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onClicked: (DialogInterface, Int) -> Unit
) =
  requireContext().singleChoiceSelector(items, checkIndex, title, onClicked)

inline fun Context.singleChoiceSelector(
  items: List<CharSequence>,
  checkIndex: Int,
  title: CharSequence? = null,
  noinline onClicked: (DialogInterface, Int) -> Unit
) =
  materialAlertDialog {
    title?.let { this.title = it }
    singleChoiceItems(items, checkIndex, onClicked)
  }.show()

inline fun Fragment.multiChoiceSelector(
  items: List<CharSequence>,
  checkItems: List<Boolean>,
  title: CharSequence? = null,
  noinline onItemSelected: (dialog: DialogInterface, index: Int, Boolean) -> Unit
) =
  requireContext().multiChoiceSelector(items, checkItems, title, onItemSelected)

inline fun Context.multiChoiceSelector(
  items: List<CharSequence>,
  checkItems: List<Boolean>,
  title: CharSequence? = null,
  noinline onItemSelected: (dialog: DialogInterface, index: Int, Boolean) -> Unit
) =
  materialAlertDialog {
    title?.let { this.title = it }
    multiChoiceItems(items, checkItems, onItemSelected)
  }.show()

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
  checkItems: List<Boolean>,
  crossinline onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit
) =
  multiChoiceItems(items.map { it.toString() }, checkItems) { dialog, which, isChecked ->
    onItemSelected(dialog, items[which], which, isChecked)
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

  fun singleChoiceItems(
    items: List<CharSequence>,
    checkedIndex: Int,
    onItemSelected: (dialog: DialogInterface, index: Int) -> Unit
  )

  fun multiChoiceItems(
    items: List<CharSequence>,
    checkedItems: List<Boolean>,
    onItemSelected: (dialog: DialogInterface, index: Int, isChecked: Boolean) -> Unit
  )

  fun build(): D
  fun show(): D
}