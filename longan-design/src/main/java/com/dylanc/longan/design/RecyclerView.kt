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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan.design

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_END
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Dylan Cai
 */

inline fun RecyclerView.setEmptyView(owner: LifecycleOwner, emptyView: View) =
  observeDataEmpty(owner) { emptyView.isVisible = it }

inline fun RecyclerView.observeDataEmpty(owner: LifecycleOwner, noinline block: (Boolean) -> Unit) {
  owner.lifecycle.addObserver(object : LifecycleObserver {
    private var observer: RecyclerView.AdapterDataObserver? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
      if (observer == null) {
        val adapter = checkNotNull(adapter) {
          "RecyclerView needs to set up the adapter before setting up an empty view."
        }
        observer = AdapterDataEmptyObserver(adapter, block)
        adapter.registerAdapterDataObserver(observer!!)
      }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
      observer?.let {
        adapter?.unregisterAdapterDataObserver(it)
        observer = null
      }
    }
  })
}

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

class AdapterDataEmptyObserver(
  private val adapter: RecyclerView.Adapter<*>,
  private val checkEmpty: (Boolean) -> Unit
) : RecyclerView.AdapterDataObserver() {

  override fun onChanged() {
    super.onChanged()
    checkEmpty(isDataEmpty)
  }

  override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
    super.onItemRangeInserted(positionStart, itemCount)
    checkEmpty(isDataEmpty)
  }

  override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
    super.onItemRangeRemoved(positionStart, itemCount)
    checkEmpty(isDataEmpty)
  }

  private val isDataEmpty get() = adapter.itemCount == 0
}
