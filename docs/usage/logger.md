# 日志用法

参考了 `AnkoLogger` 并进行了优化和改进。

### 打印日志

相对于 [android.util.Log](https://developer.android.com/reference/android/util/Log.html) 会更加易用，不需要传 tag，默认 tag 是当前的类名。例如：

```kotlin
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logInfo("test")
    logDebug(5) // 执行 `.toString()` 后再打印
    logWarn(null) // 将会打印 "null"
  }
}
```

| android.util.Log | Logger         |
| ---------------- | -------------- |
| `v()`            | `logVerbose()` |
| `d()`            | `logDebug()`   |
| `i()`            | `logInfo()`    |
| `w()`            | `logWarn()`    |
| `e()`            | `logError()`   |
| `wtf()`          | `logWtf()`     |

### 修改 Tag

实现 `Logger` 接口，重写 `loggerTag`  属性，就能修改在该类下打印日志的 tag。

```kotlin
class MainActivity : AppCompatActivity(), Logger {

  override val loggerTag: String get() = "new tag"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logInfo("test")
    logDebug(5)
    logWarn(null)
  }
}
```

或者创建 `Logger` 对象去调用对应的打印方法。

```kotlin
class MainActivity : AppCompatActivity() {

  private val logger = Logger<UserManager>() // 用其它类的类名作为 tag
  private val loggerWithSpecificTag  = Logger("my_tag")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logger.logInfo("test")
    loggerWithSpecificTag.logDebug(5)
  }
}
```

### 过滤日志

可以在初始化时配置是否打印日志。

```kotlin
initLogger(BuildConfig.DEBUG)
```

更复杂的过滤规则可以设置 `Logger.isLoggable`。

```kotlin
Logger.isLoggable = { level, tag ->
  // 可根据打印等级和标签判断是否需要打印
}
```

### 自定义打印格式

创建一个类实现 `LoggerPrinter` 接口，实现打印方法。

```kotlin
class CustomLoggerPrinter : LoggerPrinter {
  override fun log(level: LogLevel, tag: String, message: String, thr: Throwable?) {
    when (level) {
      LogLevel.VERBOSE -> //...
      LogLevel.DEBUG -> //...
      LogLevel.INFO -> //...
      LogLevel.WARN -> //...
      LogLevel.ERROR -> //...
    }
  }

  override fun logWtf(tag: String, message: String, thr: Throwable?) {
    //...
  }
}
```

初始化时设置 printer。

```kotlin
initLogger(printer = CustomLoggerPrinter())
```

后续会对默认的打印格式进行升级。