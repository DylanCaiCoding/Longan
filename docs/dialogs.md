# Dialogs 用法

保留了 `Anko` 的用法并做了优化。

## Toasts

简单地显示 [Toast](https://developer.android.com/guide/topics/ui/notifiers/toasts.html) 消息，修复了 Android 7.x 的 `BadTokenException` 异常。

```kotlin
toast("Hi there!")
toast(R.string.message)
longToast("Wow, such duration")
```

## SnackBars

简单地显示 [SnackBar](https://developer.android.com/reference/android/support/design/widget/Snackbar.html) 消息。

```kotlin
snackbar("Hi there!")
snackbar(R.string.message)
longSnackbar("Wow, such duration")
indefiniteSnackbar("Wow, always show")
snackbar("Action, reaction", "Click me!") { doStuff() }
```

## Alerts

用 DSL 语法显示[对话框](https://developer.android.com/guide/topics/ui/dialogs.html).

```kotlin
alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
  okButton { toast("Oh…") }
  cancelButton {}
}
```

上面的代码默认显示 `MaterialAlertDialog`，如果你想切换成默认使用 Appcompat 的`AlertDialog`，可以配置 `AlertFactory`：

```kotlin
initAlertBuilderFactory(Appcompat)
```

或者切换某一次对话框的样式：

```kotlin
alert(Appcompat, "Some text message")
```

默认提供 `Appcompat` 和 `Material` 可选，你可以实现 `AlertBuilderFactory` 接口进行自定义。后续会对更多的弹框样式进行支持。

## Selectors

`selector()` 显示一个带有文本项列表的对话框：

```kotlin
val countries = listOf("China", "Russia", "USA", "Australia")
selector(countries, "Where are you from?") { dialog, i ->
  toast("So you're living in ${countries[i]}, right?")
}
```

`singleChoiceSelector()` 显示一个单选列表的对话框：

```kotlin
private var checkedCountry = "China"

private fun selectCountry() {
  val countries = listOf("China", "Russia", "USA", "Australia")
  val checkedIndex = countries.indexOfFirst { it == checkedCountry }
  singleChoiceSelector(countries, checkedIndex, "Where are you from?") { dialog, i ->
    checkedCountry = countries[i]
    toast("You're living in ${checkedCountry}.")
    dialog.dismiss()
  }
}
```

`multiChoiceSelector()` 显示一个多选列表的对话框：

```kotlin
private val foods = listOf("Apple", "Banana", "Pear", "Peach")
private val checkedItems = BooleanArray(foods.size)

private fun selectFoods() {
  multiChoiceSelector(foods, checkedItems, "What do you want to eat?") { dialog, i, isChecked ->
    checkedItems[i] = isChecked
  }.doOnDismiss {
    toast("So you want to eat ${checkedItems.filter { it }.size} foods.")
  }
}
```


