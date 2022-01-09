package com.dylanc.longan.sample.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Dylan Cai
 */
class IntDiffCallback:DiffUtil.ItemCallback<Int>() {
  override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem

  override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
}