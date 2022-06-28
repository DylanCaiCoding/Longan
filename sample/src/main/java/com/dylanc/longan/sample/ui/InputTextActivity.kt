/*
 * Copyright (C) 2021. Dylan Cai
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

package com.dylanc.longan.sample.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.dylanc.longan.enableWhenOtherTextNotEmpty
import com.dylanc.longan.finishWithResult
import com.dylanc.longan.sample.databinding.ActivityInputTextBinding
import com.dylanc.viewbinding.binding
import java.io.Serializable

class InputTextActivity : AppCompatActivity() {

  private val binding: ActivityInputTextBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = intent.getStringExtra(InputTextResultContract.KEY_TITLE)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    with(binding) {
      tvName.text = intent.getStringExtra(InputTextResultContract.KEY_NAME)
      edtValue.hint = intent.getStringExtra(InputTextResultContract.KEY_HINT)
      edtValue.setText(intent.getStringExtra(InputTextResultContract.KEY_VALUE))
      btnSave.enableWhenOtherTextNotEmpty(edtValue)
      btnSave.setOnClickListener { onSave() }
    }
  }

  private fun onSave() {
    val listener = intent.getSerializableExtra(InputTextResultContract.KEY_LISTENER) as OnFilterValueListener?
    val isFilterValue = listener?.onFilterValue(binding.edtValue.text.toString())
    if (isFilterValue != true) {
      finishWithResult(InputTextResultContract.KEY_VALUE to binding.edtValue.text.toString())
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}

fun interface OnFilterValueListener : Serializable {
  fun onFilterValue(value: String): Boolean
}

class InputTextRequest(
  val name: String,
  val title: String = name,
  val hint: String? = null,
  val value: String? = null,
  val listener: OnFilterValueListener? = null
)

class InputTextResultContract : ActivityResultContract<InputTextRequest, String>() {
  override fun createIntent(context: Context, input: InputTextRequest) =
    Intent(context, InputTextActivity::class.java)
      .putExtra(KEY_NAME, input.name)
      .putExtra(KEY_TITLE, input.title)
      .putExtra(KEY_HINT, input.hint)
      .putExtra(KEY_VALUE, input.value)
      .putExtra(KEY_LISTENER, input.listener)

  override fun parseResult(resultCode: Int, intent: Intent?): String? =
    if (resultCode == Activity.RESULT_OK) intent?.getStringExtra(KEY_VALUE) else null

  companion object {
    const val KEY_TITLE = "title"
    const val KEY_NAME = "name"
    const val KEY_HINT = "hint"
    const val KEY_VALUE = "value"
    const val KEY_LISTENER = "listener"
  }
}