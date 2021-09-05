@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_END
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Dylan Cai
 */

inline fun RecyclerView.smoothScrollToStartPosition(position: Int) =
  smoothScrollToPosition(position, SNAP_TO_START)

inline fun RecyclerView.smoothScrollToEndPosition(position: Int) =
  smoothScrollToPosition(position, SNAP_TO_END)

inline fun RecyclerView.smoothScrollToPosition(position: Int, snapPreference: Int) =
  layoutManager?.let {
    val smoothScroller = LinearSmoothScroller(context, snapPreference)
    smoothScroller.targetPosition = position
    it.startSmoothScroll(smoothScroller)
  }

inline fun LinearSmoothScroller(context: Context, snapPreference: Int) =
  object : LinearSmoothScroller(context) {
    override fun getVerticalSnapPreference() = snapPreference
    override fun getHorizontalSnapPreference() = snapPreference
  }

inline fun RecyclerView.Adapter<*>.observeDataEmpty(owner: LifecycleOwner, emptyView: View) {
  val observer = AdapterDataEmptyObserver(this, emptyView)
  owner.doOnLifecycle(
    onCreate = { registerAdapterDataObserver(observer) },
    onDestroy = { unregisterAdapterDataObserver(observer) }
  )
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
    emptyView.isVisible = adapter.itemCount == 0
  }
}
