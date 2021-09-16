package com.dylanc.longan.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.addStatusBarHeightToMarginTop
import com.dylanc.longan.design.doOnDismiss
import com.dylanc.longan.design.multiChoiceSelector
import com.dylanc.longan.design.selector
import com.dylanc.longan.design.singleChoiceSelector
import com.dylanc.longan.immerseStatusBar
import com.dylanc.longan.pressBackTwiceToExit
import com.dylanc.longan.sample.R
import com.dylanc.longan.sample.adapter.TextAdapter
import com.dylanc.longan.sample.databinding.ActivityMainBinding
import com.dylanc.longan.toast
import com.dylanc.viewbinding.binding
import org.jetbrains.anko.alert

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by binding()
  private val adapter = TextAdapter(::onItemClick)
  private val items = listOf(
    R.string.selector,
    R.string.single_choice_selector,
    R.string.multi_choice_selector,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.apply {
      immerseStatusBar()
      toolbar.addStatusBarHeightToMarginTop()
      recyclerView.adapter = adapter
      adapter.submitList(items)
    }
    pressBackTwiceToExit("再次点击退出应用")
    alert {  }
  }

  private fun onItemClick(id: Int) {
    when (id) {
      R.string.selector -> checkCountry()
      R.string.single_choice_selector -> selectCountry()
      R.string.multi_choice_selector -> selectFoods()
    }
  }

  private fun checkCountry() {
    val countries = listOf("China", "Russia", "USA", "Australia")
    selector(countries, "Where are you from?") { _, i ->
      toast("So you're living in ${countries[i]}, right?")
    }
  }

  private val countries = listOf("China", "Russia", "USA", "Australia")
  private var checkedCountry = "China"
  private fun selectCountry() {
    val checkedIndex = countries.indexOfFirst { it == checkedCountry }
    singleChoiceSelector(countries, checkedIndex, "Where are you from?") { dialog, i ->
      checkedCountry = countries[i]
      toast("You're living in ${checkedCountry}.")
      dialog.dismiss()
    }
  }

  private val foods = listOf("Apple", "Banana", "Pear", "Peach")
  private val checkedItems = BooleanArray(foods.size)
  private fun selectFoods() {
    multiChoiceSelector(foods, checkedItems, "What do you want to eat?") { _, i, isChecked ->
      checkedItems[i] = isChecked
    }.doOnDismiss {
      toast("So you want to eat ${checkedItems.filter { it }.size} foods.")
    }
  }
}