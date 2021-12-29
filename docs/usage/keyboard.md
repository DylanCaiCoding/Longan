# 键盘用法

### 显示或隐藏键盘

显示键盘：

```kotlin
edtName.showKeyboard()
```

隐藏键盘：

```kotlin
edtName.hideKeyboard()
```

切换键盘：

```kotlin
edtName.toggleKeyboard()
```

判断键盘是否显示：

```kotlin
if (edtName.isKeyboardVisible) {
  // ...
}
```