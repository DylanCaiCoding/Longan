/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.longan.design

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_END
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setEmptyView(owner: LifecycleOwner, emptyView: View) =
  observeDataEmpty(owner) { emptyView.isVisible = it }

fun RecyclerView.observeDataEmpty(owner: LifecycleOwner, block: (Boolean) -> Unit) =
  owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
    private var observer: RecyclerView.AdapterDataObserver? = null

    override fun onCreate(owner: LifecycleOwner) {
      if (observer == null) {
        val adapter = checkNotNull(adapter) {
          "RecyclerView needs to set up the adapter before setting up an empty view."
        }
        observer = AdapterDataEmptyObserver(adapter, block)
        adapter.registerAdapterDataObserver(observer!!)
      }
    }

    override fun onDestroy(owner: LifecycleOwner) {
      observer?.let {
        adapter?.unregisterAdapterDataObserver(it)
        observer = null
      }
    }
  })

fun RecyclerView.smoothScrollToStartPosition(position: Int) =
  smoothScrollToPosition(position, SNAP_TO_START)

fun RecyclerView.smoothScrollToEndPosition(position: Int) =
  smoothScrollToPosition(position, SNAP_TO_END)

fun RecyclerView.smoothScrollToPosition(position: Int, snapPreference: Int) =
  layoutManager?.let {
    val smoothScroller = LinearSmoothScroller(context, snapPreference)
    smoothScroller.targetPosition = position
    it.startSmoothScroll(smoothScroller)
  }

fun LinearSmoothScroller(context: Context, snapPreference: Int) =
  object : LinearSmoothScroller(context) {
    override fun getVerticalSnapPreference() = snapPreference
    override fun getHorizontalSnapPreference() = snapPreference
  }

fun RecyclerView.addItemPadding(padding: Int) = addItemPadding(padding, padding, padding, padding)

fun RecyclerView.addItemPadding(top: Int, bottom: Int, left: Int = 0, right: Int = 0) =
  addItemDecoration(object : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
      super.getItemOffsets(outRect, view, parent, state)
      outRect.bottom = bottom
      outRect.top = top
      outRect.left = left
      outRect.right = right
    }
  })

class AdapterDataEmptyObserver(
  private val adapter: RecyclerView.Adapter<*>,
  private val checkEmpty: (Boolean) -> Unit
) : RecyclerView.AdapterDataObserver() {

  override fun onChanged() = checkEmpty(isDataEmpty)

  override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = checkEmpty(isDataEmpty)

  override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = checkEmpty(isDataEmpty)

  private val isDataEmpty get() = adapter.itemCount == 0
}
