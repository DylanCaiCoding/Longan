# SpannableString

`core-ktx` 的 Span 功能已经足够好用，但是 Span 的扩展偏少，所以本库进行了补充和完整。

## core-ktx 用法

有两种用法，第一种是将 String 转成 Spannable 对象，然后能在一个区间设置 Span 对象，比如：

```kotlin
val str = "我已同意隐私协议".toSpannable()
str[4, 8] = URLSpan("https://xxxxxxxx.com") // 设置超链接
str[4, 8] = StyleSpan(Typeface.ITALIC)  // 设置斜体
textView.movementMethod = LinkMovementMethod.getInstance() // 设置了才能点击
textView.text = str
```

第二种是通过 `buildSpannedString {...}` 函数创建 `SpannedString` 对象，在函数内调用 `append(text)` 或 `appendLine(text)` 添加文字。添加文字时可以增加 Span 效果，比如：

```kotlin
textView.movementMethod = LinkMovementMethod.getInstance() // 设置了才能点击
textView.text = buildSpannedString {
  append("我已同意")
  inSpans(URLSpan("https://xxxxxxxx.com")) { // 设置超链接
    italic { // 设置斜体
      append("隐私协议")
    }
  }
}
```

用 `inSpans(span) {...}` 函数包裹可以设置 Span，官方还提供了 `italic {...}`、`bold {...}` 等扩展函数，使用起来更加简洁。但是只有几个扩展可用，连常见的局部点击都没有。所以本库基于官方的用法进行封装，补充其余的 Span 扩展。

## Span 类和扩展

下面用一句《蜗牛》的歌词作为效果展示。

### 字符级 Span

| Span 类                       | 扩展函数                       | 作用                  | 效果图                        |
| ----------------------------- | ------------------------------ | --------------------- | ----------------------------- |
| StyleSpan(Typeface.BOLD)      | bold                           | 设置文字粗体          | ![img.png](../img/bold.png)          |
| StyleSpan(Typeface.ITALIC)    | italic                         | 设置文字斜体          | ![img.png](../img/italic.png)        |
| UnderlineSpan()               | underline                      | 设置下划线            | ![img.png](../img/underline.png)     |
| ForegroundColorSpan(color)    | color(color)                   | 设置文字颜色          | ![img.png](../img/color.png)         |
| BackgroundColorSpan(color)    | backgroundColor(color)         | 设置背景色            | ![img.png](../img/backgroudColor.png)    |
| StrikethroughSpan()           | strikeThrough                  | 设置删除线效果        | ![img.png](../img/strikeThrough.png) |
| RelativeSizeSpan(proportion)  | scale(proportion)              | 按比例缩放文字        | ![img.png](../img/scale.png)         |
| SuperscriptSpan()             | superscript                    | 设置上标文字          | ![img.png](../img/superscript.png)   |
| SubscriptSpan()               | subscript                      | 设置下标文字          | ![img.png](../img/subscript.png)     |
| AbsoluteSizeSpan(size)        | size(size)                     | 设置字体大小          | ![img.png](../img/size.png)          |
| BlurMaskFilter(radius, style) | blur(radius, [style])          | 增加蒙版              | ![img.png](../img/blur.png)          |
| TypefaceSpan(family)          | fontFamily(family)             | 设置 Android 自带字体 | ![img.png](../img/fontFamily.png)    |
| TypefaceSpanCompat(typeface)  | typeface(typeface)             | 设置指定字体          | ![img.png](../img/fontFamily.png)    |
| URLSpan(url)                  | url(url)                       | 设置超链接            | ![img.png](../img/url.png)           |
| ClickableSpan {...}           | appendClickable(text) {...}    | 设置可点击的文字      | ![img.png](../img/url.png)           |
| ImageSpan(context, imageId)   | append(imageId)                | 设置图片              | ![img.png](../img/image.png)         |
| ImageSpan + ClickableSpan     | appendClickable(imageId) {...} | 设置可点击的图片      | ![img.png](../img/image.png)         |
| SpaceSpan(size)     | appendSpace(size) | 设置空白间隔      |  ![img.png](../img/space.png) |

### 段落级 Span

| Span 类                                 | 扩展函数                   | 作用           | 效果图                        |
| --------------------------------------- | -------------------------- | -------------- | ----------------------------- |
| AlignmentSpan.Standard(ALIGN_CENTER)    | alignCenter                | 设置段落居中   | ![img.png](../img/alignCenter.png)   |
| AlignmentSpan.Standard(ALIGN_OPPOSITE)  | alignOpposite              | 设置段落居右   | ![img.png](../img/alignOpposite.png) |
| LeadingMarginSpan.Standard(first, rest) | leadingMargin(first, rest) | 设置首行缩进   | ![img.png](../img/leadingMargin.png) |
| BulletSpan(gapWidth, [color])           | bullet(gapWidth, [color])  | 设置段落符号   | ![img.png](../img/bullet.png)        |
| QuoteSpan([color])                      | quote([color])             | 设置段落引用线 | ![img.png](../img/quote.png)         |
