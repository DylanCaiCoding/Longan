package com.dylanc.longan.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.currentProcessName
import com.dylanc.longan.packageInfo
import com.dylanc.longan.sample.databinding.ActivityRecyclerViewBinding
import com.dylanc.longan.sample.databinding.ItemTextBinding
import com.dylanc.viewbinding.base.simpleStringListAdapter
import com.dylanc.viewbinding.binding

class ProcessActivity : AppCompatActivity() {

    private val binding: ActivityRecyclerViewBinding by binding()
    private val items = listOf(packageInfo.packageName, packageInfo.versionName, "process: $currentProcessName")
    private val adapter by simpleStringListAdapter<ItemTextBinding> {
        tvTitle.text = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.adapter = adapter
        adapter.submitList(items)
    }
}