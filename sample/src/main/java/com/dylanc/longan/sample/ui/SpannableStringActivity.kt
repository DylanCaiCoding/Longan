package com.dylanc.longan.sample.ui

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.*
import com.dylanc.longan.*
import com.dylanc.longan.sample.R
import com.dylanc.longan.sample.databinding.ActivitySpannableStringBinding
import com.dylanc.viewbinding.binding

/**
 * @author Dylan Cai
 */
class SpannableStringActivity : AppCompatActivity() {

  private val binding: ActivitySpannableStringBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTitle(R.string.spannable_string)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    binding.apply {
      textView.text = buildSpannedString {
        appendLine()
        append("我要一步一步往上爬，")
        bold {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        italic {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        underline {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        color(Color.RED) {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        backgroundColor(Color.RED) {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        strikeThrough {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        scale(1.3f) {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        superscript {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        subscript {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        size(11.sp) {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        blur(10f) {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        fontFamily("serif") {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        url("https://www.baidu.com") {
          appendLine("在最高点乘着叶片往前飞")
        }
        appendLine()

        append("我要一步一步往上爬，")
        appendClickable("在最高点乘着叶片往前飞") { toast("text") }
        appendLine()
        appendLine()

        append("我要一步一步往上爬，")
        append("在最高点乘着")
        append(R.drawable.leaf)
        appendLine("往前飞")
        appendLine()

        append("我要一步一步往上爬，")
        append("在最高点乘着")
        appendClickable(R.drawable.leaf) { toast("image") }
        appendLine("往前飞")
        appendLine()

        append("我要一步一步往上爬，")
        appendSpace(20.dp)
        appendLine("在最高点乘着叶片往前飞")
        appendLine()

        alignCenter {
          appendLine("我要一步一步往上爬，在最高点乘着叶片往前飞")
        }
        appendLine()

        alignOpposite {
          appendLine("我要一步一步往上爬，在最高点乘着叶片往前飞")
        }
        appendLine()

        leadingMargin(textView.textSize * 2) {
          appendLine("我要一步一步往上爬，在最高点乘着叶片往前飞")
        }
        appendLine()

        bullet(4.dp) {
          appendLine("我要一步一步往上爬，在最高点乘着叶片往前飞")
        }
        appendLine()

        quote {
          appendLine("我要一步一步往上爬，在最高点乘着叶片往前飞")
        }
        appendLine()
      }
      textView.movementMethod = LinkMovementMethod.getInstance()
      textView.transparentHighlightColor()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}