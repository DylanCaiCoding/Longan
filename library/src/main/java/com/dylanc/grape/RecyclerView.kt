@file:Suppress("unused")

package com.dylanc.grape

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Dylan Cai
 */

fun RecyclerView.Adapter<*>.observeDataEmpty(emptyView: View) {
  registerAdapterDataObserver(AdapterDataEmptyObserver(this, emptyView))
}

class AdapterDataEmptyObserver(
  private val adapter: RecyclerView.Adapter<*>,
  private val emptyView: View
) : RecyclerView.AdapterDataObserver() {

  override fun onChanged() {
    super.onChanged()
    checkEmpty()
  }

  override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
    super.onItemRangeInserted(positionStart, itemCount)
    checkEmpty()
  }

  override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
    super.onItemRangeRemoved(positionStart, itemCount)
    checkEmpty()
  }

  private fun checkEmpty() {
    if (adapter.itemCount == 0) {
      emptyView.visibility = View.VISIBLE
    } else {
      emptyView.visibility = View.GONE
    }
  }
}