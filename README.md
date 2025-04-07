# Longan

English | [中文](README_CN.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/Longan.svg)](https://www.jitpack.io/#DylanCaiCoding/Longan) [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/Longan/blob/master/LICENSE)

Longan is a collection of Kotlin utils which makes Android application development faster and easier. It makes your code clean and easy to read.

## Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

Add dependencies：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.1.1'
    // Optional
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.1.1'
}
```
## Usage

:pencil: **[>> Usage documentation <<](https://dylancaicoding.github.io/Longan)**

## Sample

The following describes some common functions.

When you need Context or Activity, you can get the `application` or `topActivity` property directly.

Start the Activity and pass the parameters:

```kotlin
startActivity<SomeOtherActivity>("id" to 5)
```

Use property delegates to get parameters within your Activity:

```kotlin
class SomeActivity : AppCompatActivity() {
  private val name: String? by intentExtras("name")          // Get nullable parameter with the Intent
  private val position: Int by intentExtras("position", 0)   // Get a non-null parameter with a default value with the Intent
  private val id: String by safeIntentExtras("id")           // Get an artificially non-null parameter with the Intent
}
```

Create the Fragment and pass the parameters:

```kotlin
val fragment = SomeFragment().withArguments("id" to 5)
```

Use property delegates to get parameters within your Fragment:

```kotlin
class SomeFragment : Fragment() {
  private val name: String? by arguments("name")          // Get nullable parameter with the arguments
  private val position: Int by arguments("position", 0)   // Get a non-null parameter with a default value with the arguments
  private val id: String by safeArguments("id")           // Get an artificially non-null parameter with the arguments
}
```

Simply shows a Toast or Snackbar message:

```kotlin
toast("Hi there!")
snackbar("Action, reaction", "Click me!") { doStuff() }
```

Show or hide the keyboard:

```kotlin
editText.showKeyboard()
editText.hideKeyboard()
```

Use TabLayout + ViewPager2 to implement the bottom navigation bar of custom style:

```kotlin
viewPager2.adapter = FragmentStateAdapter(HomeFragment(), ShopFragment(), MineFragment())
tabLayout.setupWithViewPager2(viewPager2, enableScroll = false) { tab, position ->
  tab.setCustomView(R.layout.layout_bottom_tab) {
    findViewById<TextView>(R.id.tv_title).setText(titleList[position])
    findViewById<ImageView>(R.id.iv_icon).apply {
      setImageResource(iconList[position])
      contentDescription = getString(titleList[position])
    }
  }
}
```

A line of code to double click the back key to exit App or click the back key not to exit App back to the desktop:

```kotlin
pressBackTwiceToExitApp("To exit app, pressing again")
// pressBackToNotExitApp()
```

Immersive status bar, and increase the height of the status bar to the top margin of the title bar, can be adapted to the notch screen:

```kotlin
immerseStatusBar()
toolbar.addStatusBarHeightToMarginTop()
// toolbar.addStatusBarHeightToPaddingTop()
```

Achieve the countdown to obtain the verification code:

```kotlin
btnSendCode.startCountDown(this,
  onTick = {
    text = "${it} second"
  },
  onFinish = {
    text = "send"
  })
```

Make the button click only when the input box has content:

```kotlin
btnLogin.enableWhenOtherTextNotEmpty(edtAccount, edtPwd)
```

Click event can set the click interval to prevent repeated clicking within a period of time:

```kotlin
btnLogin.doOnClick(clickIntervals = 500) { 
  // ...
}
```

Automatically show an empty layout when RecyclerView's data is empty:

```kotlin
recyclerView.setEmptyView(this, emptyView)
```

RecyclerView scrolls smoothly to the starting position:

```kotlin
recyclerView.smoothScrollToStartPosition(position)
```

Simplifies custom view getting custom properties:

```kotlin
withStyledAttributes(attrs, R.styleable.CustomView) {
  textSize = getDimension(R.styleable.CustomView_textSize, 12.sp)
  textColor = getColor(R.styleable.CustomView_textColor, getCompatColor(R.color.text_normal))
  icon = getDrawable(R.styleable.CustomView_icon) ?: getCompatDrawable(R.drawable.default_icon)
  iconSize = getDimension(R.styleable.CustomView_iconSize, 30.dp)
}
```

Custom view draws centered or vertically centered text:

```kotlin
canvas.drawCenterText(text, centerX, centerY, paint)
canvas.drawCenterVerticalText(text, centerX, centerY, paint)
```

Switch to the main thread：

```kotlin
mainThread { 
  // ...
}
```

See the [usage documentation](https://dylancaicoding.github.io/Longan) for more usage.

## Change log

[Releases](https://github.com/DylanCaiCoding/Longan/releases)

## Author's other libraries

| Library                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) | Decoupling the code of toolbar or loading status view. |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX) | The most comprehensive utils of ViewBinding.                 |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)       | Use MMKV with property delegates.                                  |
| [MultiBaseUrls](https://github.com/DylanCaiCoding/MultiBaseUrls) | Use annotation to allow Retrofit to support multiple baseUrl and dynamically change baseUrl |
| [Tracker](https://github.com/DylanCaiCoding/Tracker)       | A lightweight tracking framework based on the tracking idea of Buzzvideo.|

## License

```
Copyright (C) 2021. Dylan Cai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
