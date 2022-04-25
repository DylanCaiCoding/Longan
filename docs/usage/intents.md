# Intents 用法

参考和保留了 `Anko` 部分功能。

## 创建 Intent

通常 Intent 每增加一个额外的参数或 flag，就需要增加一行代码。例如：

```kotlin
val intent = Intent(this, SomeOtherActivity::class.java)
intent.putExtra("id", 5)
intent.setFlag(Intent.FLAG_ACTIVITY_SINGLE_TOP)
startActivity(intent)
```

而 `Longan` 提供了更简单的方式实现上面的功能。

```kotlin
startActivity(intentOf<SomeOtherActivity>("id" to 5).singleTop())
```

## 获取  Intent 的参数

使用 `intentExtras()` 委托获取 `Intent` 的数据。

```kotlin
val name: String? by intentExtras(KEY_NAME)
val count: Int by intentExtras(KEY_COUNT, default = 0)
```

如果可以人为确保一定能获取到值，又不想传默认值，可以用 `safeIntentExtras()` 进行委托。

```kotlin
val phone: String by safeIntentExtras(KEY_PHONE)
```

## 常用的 Intent 操作

拨号（并未呼叫），无需权限:

```kotlin
dial(number)
```

拨打电话，需要 `CALL_PHONE` 权限:

```kotlin
makeCall(number)
```

发送短信：

```kotlin
sendSMS(number, content)
```

发送邮件：

```kotlin
email(email, subject, text)
```

安装 APK：

```kotlin
installAPK(uri)
```

以上方法如果发送了 `Intent`，则方法返回 true。

## 分享的 Intent

分享功能是用 `ShareCompat` 实现，无返回值。

分享文字：

```kotlin
shareText(text)
```

分享单张图片：

```kotlin
shareImage(uri)
```

分享多张图片：

```kotlin
shareImages(uris)
```

分享文字和单张图片：

```kotlin
shareTextAndImage(text, uri)
```

分享文字和多张图片：

```kotlin
shareTextAndImages(text, uris)
```

分享单个文件：

```kotlin
shareFile(uri)
```


分享多个文件：

```kotlin
shareFiles(uris)
```