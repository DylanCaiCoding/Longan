@file:Suppress("unused")

package com.dylanc.longan

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author Dylan Cai
 */

inline fun Fragment.materialAlertDialog(block: AlertBuilder<*>.() -> Unit) =
  requireContext().materialAlertDialog(block)

inline fun Context.materialAlertDialog(block: AlertBuilder<*>.() -> Unit) =
  MaterialAlertBuilder(this).apply(block)

class MaterialAlertBuilder(override val context: Context) : AlertBuilder<AlertDialog> {

  private val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(context)

  override var title: CharSequence
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setTitle(value)
    }

  override var titleResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setTitle(value)
    }

  override var message: CharSequence
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setMessage(value)
    }

  override var messageResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setMessage(value)
    }

  override var icon: Drawable
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setIcon(value)
    }

  override var iconResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setIcon(value)
    }

  override var customTitle: View
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setCustomTitle(value)
    }

  override var customView: View
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
    get() = noGetter()
    set(value) {
      builder.setView(value)
    }

  override var isCancelable: Boolean
    @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
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
    checkedItems: List<Boolean>,
    onItemSelected: (DialogInterface, Int, Boolean) -> Unit
  ) {
    builder.setMultiChoiceItems(items.toTypedArray(), checkedItems.toBooleanArray()) { dialog, which, isChecked ->
      onItemSelected(dialog, which, isChecked)
    }
  }

  override fun build(): AlertDialog = builder.create()

  override fun show(): AlertDialog = builder.show()
}