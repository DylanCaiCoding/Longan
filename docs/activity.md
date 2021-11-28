# Activity 用法

### 跳转 Activity

简单地跳转：

```kotlin
startActivity<SomeOtherActivity>()
```

带参数跳转：

```kotlin
startActivity<SomeOtherActivity>("id" to 5)
```

带参数跳转推荐用下面的写法：

```kotlin
class SomeActivity : AppCompatActivity() {
  private val id: String by safeIntentExtras(KEY_ID)

  //...

  companion object {
    fun start(id: String) = startActivity<SomeActivity>(KEY_ID to id)
  }
}
```

```kotlin
SomeActivity.start(id)
```

编写一个静态的 `start()` 方法传入所需的参数并跳转，在跳转的页面通过委托的方式获取值，可查看 [Intent](/intents) 的用法。调用静态方法跳转可以避免传漏或传错参数。

### 结束 Activity 时回调结果

在 `startActivityForResult()` 跳转的页面通常需要在结束的时候带上回调的结果，一般需要几行代码来实现。比如：

```kotlin
val intent = Intent()
intent.putExtra("id", 5)
intent.putExtra("name", name)
setResult(RESULT_OK, intent)
finish()
```

几行代码有点繁琐，所以提供了一种更简单的方式能用一行代码实现：

```kotlin
finishWithResult("id" to 5, "name" to name)
```

### Activity 堆栈管理

可使用 `topActivity` 获取栈顶的 Activity，使用 `activityList` 获取 Activity 堆栈的列表。

判断 Activity 是否在堆栈中：

```kotlin
if (isActivityExistsInStack<SomeActivity>()) {
  // ...
}
```

结束某个 Activity：

```kotlin
finishActivity<SomeActivity>()
```

结束所有 Activity:

```kotlin
finishAllActivities()
```

结束除了最新以外的所有 Activity:

```kotlin
finishAllActivitiesExceptNewest()
```

### 双击返回键退出

快速实现双击返回键退出：

```kotlin
pressBackTwiceToExitApp("再次点击退出应用")
```

默认 2 秒内第二次点击返回键才退出，可以传多一个参数修改间隔时间：

```kotlin
pressBackTwiceToExitApp("再次点击退出应用", delayMillis = 1500)
```

如果需要自定义吐司或者换成 `Snackbar`：

```kotlin
pressBackTwiceToExitApp {
  snackbar("再次点击退出应用")
}
```

### 点击返回键不退出

有时需要点击返回不退出应用，直接返回桌面：

```kotlin
pressBackToNotExitApp()
```

### 强转 Activity

有些时候作用域内的 this 不是 Activity，这时我们用到 Activity 就要指明用哪个 this，比如 `this@SignInActivity`。所以增加了拓展属性 `context`, `activity`, `fragmentActivity`, `lifecycleOwner`，将 Activity 强转成对应的类型进行使用，可以简化代码，提高代码的可读性。