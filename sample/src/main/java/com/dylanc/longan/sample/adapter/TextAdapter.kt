package com.dylanc.longan.sample.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dylanc.longan.sample.databinding.ItemTextBinding
import com.dylanc.viewbinding.BindingViewHolder
import com.dylanc.viewbinding.onItemClick

/**
 * @author Dylan Cai
 */
class TextAdapter(
  private val onItemClick: (Int) -> Unit
) : ListAdapter<Int, BindingViewHolder<ItemTextBinding>>(DiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BindingViewHolder<ItemTextBinding>(parent)
      .onItemClick { onItemClick(currentList[it]) }

  override fun onBindViewHolder(holder: BindingViewHolder<ItemTextBinding>, position: Int) {
    holder.binding.apply {
      tvTitle.setText(currentList[position])
    }
  }

  class DiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
  }
}