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
        bold { appendLine("bold") }
        italic { appendLine("italic") }
        underline { appendLine("underline") }
        color(Color.RED) { appendLine("color(Color.RED)") }
        backgroundColor(Color.GRAY) { appendLine("backgroundColor(Color.GRAY)") }
        strikeThrough { appendLine("strikeThrough") }
        scale(2f) { appendLine("scale(2f)") }
        append("content")
        superscript { appendLine("superscript") }
        appendLine("content")
        subscript { append("subscript") }
        size(18.sp) { appendLine("size(18.sp)") }
        alignCenter { appendLine("alignCenter") }
        alignOpposite { appendLine("alignOpposite") }
        leadingMargin(textView.textSize * 2) { appendLine("leadingMargin(textSize * 2)") }
        append("blur(10f): ")
        blur(10f) { appendLine("blur(10f)") }
        append("url(")
        url("https://www.baidu.com") { append("https://www.baidu.com") }
        appendLine(")")
        fontFamily("monospace") { appendLine("fontFamily(\"monospace\")") }
        bullet(8.dp) { appendLine("bullet(8.dp)") }
        quote(Color.RED) { appendLine("quote(Color.RED)") }
        appendClickable(R.mipmap.ic_launcher) { toast("click image") }
        appendLine()
        appendClickable("appendClickable") { toast("click text") }
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