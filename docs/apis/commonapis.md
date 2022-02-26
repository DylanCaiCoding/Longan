# Common APIs

## Gradle

添加依赖到 `build.gradle`：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.0.5'
}
```

## APIs

下面用法中含有方括号 `[]` 的参数是可选的。

### [Activity.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Activity.kt)

| 用法                                                         | 作用                                                |
| ------------------------------------------------------------ | --------------------------------------------------- |
| `[Activity].startActivity<SomeOtherActivity>("id" to 5)`     | 启动 Activity                                       |
| `Activity.finishWithResult("id" to 5, "name" to name)`       | 关闭 Activity 并返回结果                            |
| `activityList`                                               | 获取 Activity 栈链表                                |
| `topActivity`                                                | 获取栈顶 Activity                                   |
| `isActivityExistsInStack<SomeOtherActivity>`                 | 判断 Activity 是否存在栈中                          |
| `finishActivity<SomeOtherActivity>`                          | 结束 Activity                                       |
| `finishAllActivities`                                        | 结束所有 Activity                                   |
| `finishAllActivitiesExceptNewest`                            | 结束除最新之外的所有 Activity                       |
| `ComponentActivity.pressBackTwiceToExitApp(toastText, [delayMillis])` | 双击返回退出 App                                    |
| `ComponentActivity.pressBackTwiceToExitApp([delayMillis]) {...}` | 双击返回退出 App，自定义 Toast                      |
| `ComponentActivity.pressBackToNotExit()`                     | 点击返回不退出 App                                  |
| `Context.checkPermission(permission)`                        | 判断是否有权限                                      |
| `Activity.windowInsetsControllerCompat`                      | 获取 WindowInsetsControllerCompat                   |
| `Activity.decorFitsSystemWindows`                            | 设置布局适应系统窗口                                |
| `Activity.contentView`                                       | 获取布局的根视图                                    |
| `Context.activity`                                           | 通过 Context 获取 Activity                          |
| `Context.context`                                            | 作用域的 this 不是 Activity 时获取 Context          |
| `Activity.activity`                                          | 作用域的 this 不是 Activity 时获取 Activity         |
| `FragmentActivity.fragmentActivity`                          | 作用域的 this 不是 Activity 时获取 FragmentActivity |
| `ComponentActivity.lifecycleOwner`                           | 作用域的 this 不是 Activity 时获取 LifecycleOwner   |

### [ActivityResult.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/ActivityResult.kt)

| 用法                                                         | 作用                                         |
| ------------------------------------------------------------ | -------------------------------------------- |
| `ActivityResultCaller.startActivityLauncher {...}`           | 注册 `startActivityForResult()` 的启动器     |
| `ActivityResultCaller.startIntentSenderLauncher {...}`       | 注册 `startIntentSenderForResult()` 的启动器 |
| `ActivityResultCaller.requestPermissionLauncher {...}`       | 注册请求单个权限的启动器                     |
| `ActivityResultCaller.requestMultiplePermissionsLauncher {...}` | 注册请求多个权限的启动器                     |
| `ActivityResultCaller.takePicturePreviewLauncher {...}`      | 注册拍照预览的启动器                         |
| `ActivityResultCaller.takePictureLauncher {...}`             | 注册拍照的启动器                             |
| `ActivityResultCaller.takeVideoLauncher {...}`               | 注册录像的启动器                             |
| `ActivityResultCaller.pickContactLauncher{...}`              | 注册选择联系人的启动器                       |
| `ActivityResultCaller.pickContentLauncher {...}`             | 注册选择单个图片、视频的启动器               |
| `ActivityResultCaller.getContentLauncher {...}`              | 注册选择单个图片、视频的启动器               |
| `ActivityResultCaller.getMultipleContentsLauncher {...}`     | 注册选择多个图片、视频的启动器               |
| `ActivityResultCaller.openDocumentLauncher {...}`            | 注册打开单个文档的启动器                     |
| `ActivityResultCaller.openMultipleDocumentsLauncher {...}`   | 注册打开多个文档的启动器                     |
| `ActivityResultCaller.openDocumentTreeLauncher {...}`        | 注册打开文档目录的启动器                     |
| `ActivityResultCaller.createDocumentLauncher {...}`          | 注册创建文档的启动器                         |
| `ActivityResultCaller.launchAppSettingsLauncher {...}`       | 注册启动 App 设置页的启动器                  |
| `ActivityResultCaller.cropPictureLauncher {...}`             | 注册裁剪图片的启动器                         |
| `ActivityResultCaller.enableLocationLauncher {...}`          | 注册开启定位的启动器                         |
| `ActivityResultCaller.enableBluetoothLauncher {...}`         | 注册开启蓝牙的启动器                         |
| `ActivityResultCaller.launchWifiSettingsLauncher {...}`      | 注册启动 Wifi 设置页的启动器                 |
| `ActivityResultCaller.openWifiPanelLauncher {...}`           | 注册打开 Wifi 面板的启动器                   |
| `ActivityResultLauncher<Unit/Viod>.launch()`                 | 启动无参的启动器                             |
| `ActivityResultLauncher<Array<T>>.launch(varage input)`      | 启动可变参数的启动器                         |
| `ActivityResultLauncher<Intent>.launch<SomeActivity>(...)`   | 启动 `startActivityForResult()` 的启动器     |
| `ActivityResultLauncher<IntentSenderRequest>.launch(intentSender, ...)` | 启动 `startIntentSenderForResult()` 的启动器 |
| `ActivityResultLauncher<CropPictureRequest>.launch(inputUri, ...)` | 启动裁剪图片的启动器                         |

### [Application.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Application.kt)

| 用法                         | 作用                       |
| ---------------------------- | -------------------------- |
| `application`                | 获取 Application           |
| `packageName`                | 获取包名                   |
| `appVersionName`             | 获取 App 版本号            |
| `appVersionCode`             | 获取 App 版本码            |
| `isAppDebug`                 | 判断 App 是否是 Debug 版本 |
| `isAppDarkMode`              | 判断 App 是否是夜间模式    |
| `launchAppDetailsSettings`   | 启动 App 详情设置          |
| `relaunchApp([killProcess])` | 重启 App                   |

### [Arrays.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Arrays.kt)

| 用法                        | 作用                         |
| --------------------------- | ---------------------------- |
| `Array<T>.percentage {...}` | 获取条件判断所占数量的百分比 |

### [Bluetooth.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Bluetooth.kt)

| 用法                           | 作用                   |
| ------------------------------ | ---------------------- |
| `isBluetoothEnabled`           | 判断蓝牙是否开启       |
| `BluetoothStateLiveData {...}` | 监听蓝牙设备的连接状态 |

### [Canvas.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Canvas.kt)

| 用法                                                         | 作用               |
| ------------------------------------------------------------ | ------------------ |
| `Canvas.drawCenterVerticalText(text, centerX, centerY, paint)` | 绘制垂直居中的文字 |
| `Canvas.drawCenterText(text, centerX, centerY, paint)`       | 绘制居中的文字     |

### [Clipboard.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Clipboard.kt)

| 用法                                                     | 作用             |
| -------------------------------------------------------- | ---------------- |
| `CharSequence/Uri/Intent.copyToClipboard([label])`       | 复制到剪贴板     |
| `getTextFromClipboard()`                                 | 获取剪贴板的文本 |
| `clearClipboard()`                                       | 清理剪贴板      |
| `doOnClipboardChanged(listener)`                         | 监听剪贴板的变化 |
| `ClipboardManager.OnPrimaryClipChangedListener.cancel()` | 移除剪贴板监听器 |

### [Collections.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Collections.kt)

| 用法                       | 作用                         |
| -------------------------- | ---------------------------- |
| `List<T>.percentage {...}` | 获取条件判断所占数量的百分比 |

### [ContentResolver.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/ContentResolver.kt)

| 用法                                                         | 作用                   |
| ------------------------------------------------------------ | ---------------------- |
| `contentResolver`                                            | 获取 ContentResolver   |
| `ContentResolver.query(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询匹配的 Uri         |
| `ContentResolver.queryFirst(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询第一个匹配的 Uri   |
| `ContentResolver.queryLast(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询最后一个匹配的 Uri |
| `ContentResolver.queryMediaImages(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询匹配的图片 Uri     |
| `ContentResolver.queryMediaVideos(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询匹配的视频 Uri     |
| `ContentResolver.queryMediaAudios(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询匹配的音频 Uri     |
| `ContentResolver.queryMediaDownloads(uri, [projection], [selection], [selectionArgs], [sortOrder]) {...}` | 查询匹配的下载 Uri     |
| `ContentResolver.insert(uri, [block])`                       | 插入 Uri               |
| `ContentResolver.insertMediaImage(uri, [block])`             | 插入图片 Uri           |
| `ContentResolver.insertMediaVideo([block])`                  | 插入视频 Uri           |
| `ContentResolver.insertMediaAudio([block])`                  | 插入音频 Uri           |
| `ContentResolver.insertMediaDownload([block])`               | 插入下载 Uri           |
| `ContentResolver.update(uri, [where], [selectionArgs]) {...}` | 更新 Uri               |
| `ContentResolver.delete(uri, [where], [selectionArgs])`      | 删除 Uri               |

### [ContentValues.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/ContentValues.kt)

| 用法                             | 作用                                         |
| -------------------------------- | -------------------------------------------- |
| `contentValues {...}`            | 创建 ContentValues 对象                      |
| `ContentValues.displayName`      | 设置 `MediaStore.MediaColumns.DISPLAY_NAME`  |
| `ContentValues.relativePath`     | 设置 `MediaStore.MediaColumns.RELATIVE_PATH` |
| `ContentValues.mimeType: String` | 设置 `MediaStore.MediaColumns.MIME_TYPE`     |
| `ContentValues.imageDescription` | 设置 `MediaStore.Images.Media.DESCRIPTION`   |
| `ContentValues.videoDescription` | 设置 `MediaStore.Video.Media.DESCRIPTION`    |
| `ContentValues.dateAdded`        | 设置 `MediaStore.MediaColumns.DATE_ADDED`    |
| `ContentValues.dateModified`     | 设置 `MediaStore.MediaColumns.DATE_MODIFIED` |

### [Crash.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Crash.kt)

| 用法                             | 作用               |
| -------------------------------- | ------------------ |
| `saveCrashLogLocally([dirPath])` | 保存崩溃日志到本地 |
| `handleUncaughtException {...}`  | 处理未捕获的异常   |

### [DateTime.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/DateTime.kt)

| 用法                                                         | 作用                                       |
| ------------------------------------------------------------ | ------------------------------------------ |
| `Instant.Companion.parse(text, pattern, [timeZone])`         | 字符串转 Instant                           |
| `LocalDateTime.Companion.parse(text, pattern)`               | 字符串转 LocalDateTime                     |
| `LocalDate.Companion.parse(text, pattern)`                   | 字符串转 LocalDate                         |
| `String.toInstant(pattern, [timeZone])`                      | 字符串转 Instant                           |
| `String.toLocalDateTime(pattern)`                            | 字符串转 LocalDateTime                     |
| `String.toLocalDate(pattern)`                                | 字符串转 LocalDate                         |
| `Instant.format(pattern, [timeZone])`                        | Instant 转字符串                           |
| `LocalDateTime.format(pattern)`                              | LocalDateTime 转字符串                     |
| `LocalDate.format(pattern)`                                  | LocalDateTime 转字符串                     |
| `LocalDateTime/LocalDate.isToday([timeZone])`                | 判断是不是今天                              |
| `LocalDateTime/LocalDate.isYesterday([timeZone])`            | 判断是不是昨天                              |
| `LocalDateTime/LocalDate.withYear(year))`                    | 修改年份                                   |
| `LocalDateTime/LocalDate.withMonth(month))`                  | 修改月份                                   |
| `LocalDateTime/LocalDate.withDayOfMonth(dayOfMonth)`         | 修改当月的天数                              |
| `LocalDateTime/LocalDate.withDayOfYear(dayOfYear)`           | 修改今年的天数                              |
| `LocalDateTime/LocalDate.with {...})`                        | 修改日期或时间                              |
| `LocalDateTime.withHour(hour)`                               | 修改小时                                   |
| `LocalDateTime.withMinute(minute)`                           | 修改分钟                                   |
| `LocalDateTime.withSecond(second)`                           | 修改秒                                     |
| `LocalDateTime.withNano(nano)`                               | 修改纳秒                                   |
| `LocalDateTime/LocalDate.firstDayOfYear()`                   | 今年的第一天                                |
| `LocalDateTime/LocalDate.lastDayOfYear()`                    | 今年的最后一天                              |
| `LocalDateTime/LocalDate.firstDayOfNextYear()`               | 名年的第一天                                |
| `LocalDateTime/LocalDate.firstDayOfLastYear()`               | 去年的第一天                                |
| `LocalDateTime/LocalDate.firstDayOfMonth()`                  | 这个月的第一天                              |
| `LocalDateTime/LocalDate.lastDayOfMonth()`                   | 这个月的最后一天                            |
| `LocalDateTime/LocalDate.firstDayOfNextMonth()`              | 下个月的第一天                              |
| `LocalDateTime/LocalDate.firstDayOfLastMonth()`              | 上个月的第一天                              |
| `LocalDateTime/LocalDate.firstInMonth(dayOfWeek)`            | 这个月的第一个周几                           |
| `LocalDateTime/LocalDate.lastInMonth(dayOfWeek)`             | 这个月的最后一个周几                         |
| `LocalDateTime/LocalDate.dayOfWeekInMonth(ordinal, dayOfWeek)` | 这个月的第几个周几                         |
| `LocalDateTime/LocalDate.next(dayOfWeek)`                    | 下一个周几，不包含今天                        |
| `LocalDateTime/LocalDate.nextOrSame(dayOfWeek)`              | 下一个周几，包含今天                         |
| `LocalDateTime/LocalDate.previous(dayOfWeek)`                | 上一个周几，不包含今天                       |
| `LocalDateTime/LocalDate.previousOrSame(dayOfWeek)`          | 上一个周几，包含今天                         |
| `Instant.plus(period)`                                       | 增加一个周期                               |
| `Instant.plus([value], unit)`                                | 增加指定数量的日期或时间单位                  |
| `Instant.minus(period)`                                      | 减少一个周期                               |
| `Instant.minus([value], unit)`                               | 减少指定数量的日期或时间单位                  |
| `Instant.until(instant, unit)`                               | 将两时刻差值计算为指定日期或时间单位的数量      |
| `Instant.daysUntil(instant)`                                 | 两时刻相差的多少天                          |
| `Instant.monthsUntil(instant)`                               | 两时刻相差的多少个月                        |
| `Instant.yearsUntil(instant)`                                | 两时刻相差的多少年                          |
| `Instant.periodUntil(instant)`                               | 两时刻相差的时间段                          |

### [DeviceInfo.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/DeviceInfo.kt)

| 用法                 | 作用               |
| -------------------- | ------------------ |
| `sdkVersionName`     | 获取设备系统版本号 |
| `sdkVersionCode`     | 获取设备系统版本码 |
| `deviceManufacturer` | 获取设备厂商       |
| `deviceModel`        | 获取设备型号       |

### [Dimensions.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Dimensions.kt)

| 用法                             | 作用     |
| -------------------------------- | -------- |
| `Int/Long/Float/Double.dp`       | dp 转 px |
| `Int/Long/Float/Double.sp`       | sp 转 px |
| `Int/Long/Float/Double.pxToDp()` | px 转 dp |
| `Int/Long/Float/Double.pxToSp()` | px 转 sp |

### [DownloadManager.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/DownloadManager.kt)

| 用法                  | 作用                              |
| --------------------- | --------------------------------- |
| `download(url) {...}` | 使用原生 DownloadManager 下载文件 |

### [Encode.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Encode.kt)

| 用法                                     | 作用        |
| ---------------------------------------- | ----------- |
| `ByteArray.base64Encode([flag])`         | Base64 编码 |
| `ByteArray.base64EncodeToString([flag])` | Base64 编码 |
| `String.base64Decode([flag])`            | Base64 解码 |
| `String.urlEncode(enc)`                  | Url 编码    |
| `String.urlDecode(enc)`                  | Url 解码    |

### [Encrypt.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Encrypt.kt)

| 用法                                   | 作用             |
| -------------------------------------- | ---------------- |
| `String/ByteArray.encrtpyMD5()`        | MD5 加密         |
| `String/ByteArray.encrtpySHA1()`       | SHA1 加密        |
| `String/ByteArray.encrtpySHA256()`     | SHA256 加密      |
| `String/ByteArray.encrtpySHA512()`     | SHA512 加密      |
| `String/ByteArray.encrtpyHmacSHA1(key)`   | HMAC-SHA1 加密   |
| `String/ByteArray.encrtpyHmacSHA256(key)` | HMAC-SHA256 加密 |
| `String/ByteArray.encrtpyHmacSHA512(key)` | HMAC-SHA512 加密 |

### [File.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/File.kt)

| 用法                                    | 作用                               |
| --------------------------------------- | ---------------------------------- |
| `File.isExistOrCreateNewFile()`         | 判断是否存在，不存在则创建新文件   |
| `File?.isExistOrCreateNewDir()`         | 判断是否存在，不存在则创建新文件夹 |
| `File.createNewFileAfterDeleteExist()`  | 创建新文件，如果文件已存在则先删除 |
| `File.rename(name)`                     | 重命名文件                         |
| `File.mimeType`                         | 获取文件的 MIME 类型               |
| `fileSeparator`                         | 获取文件分隔符                     |
| `File.print {...}`                      | 打印内容到文件                     |
| `File.checkMD5(md5)`                    | 检测 MD5 的值                      |
| `File.checkSHA1(sha1)`                  | 检测 SHA1 的值                     |
| `File.checkSHA256(sha256)`              | 检测 SHA256 的值                   |
| `File.checkSHA512(sha512)`              | 检测 SHA512 的值                   |
| `File.checkHmacSHA1(key, hmacSHA1)`     | 检测 HMAC-SHA1 的值                |
| `File.checkHmacSHA256(key, hmacSHA256)` | 检测 HMAC-SHA256 的值              |
| `File.checkHmacSHA512(key, hmacSHA512)` | 检测 HMAC-SHA512 的值              |
| `File.calculateMD5()`                   | 计算 MD5 的值                      |
| `File.calculateSHA1()`                  | 计算 SHA1 的值                     |
| `File.calculateSHA256()`                | 计算 SHA256 的值                   |
| `File.calculateSHA512()`                | 计算 SHA512 的值                   |
| `File.calculateHmacSHA1(key)`           | 计算 HMAC-SHA1 的值                |
| `File.calculateHmacSHA256(key)`         | 计算 HMAC-SHA256 的值              |
| `File.calculateHmacSHA512(key)`         | 计算 HMAC-SHA512 的值              |

### [Flow.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Flow.kt)

| 用法                                                        | 作用                           |
| ----------------------------------------------------------- | ------------------------------ |
| `Flow<T>.launchAndCollectIn(owner, [minActiveState]) {...}` | 启动 lifecycleScope 并收集数据 |

### [Fragment.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Fragment.kt)

| 用法                                  | 作用                                             |
| ------------------------------------- | ------------------------------------------------ |
| `Fragment.withArguments("id" to 5)`   | 创建 Fragment 时伴随参数                         |
| `by Fragment.arguments(key)`          | 通过 Fragment 的 argments 获取可空的参数         |
| `by Fragment.arguments(key, default)` | 通过 Fragment 的 argments 获取含默认值的参数     |
| `by Fragment.safeArguments(key)`      | 通过 Fragment 的 argments 获取人为保证非空的参数 |

### [Intents.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Intents.kt)

| 用法                                     | 作用                                            |
| ---------------------------------------- | ----------------------------------------------- |
| `intentOf<SomeOtherActivity>("id" to 5)` | 创建 Intent                                     |
| `by Activity.intentExtras(key)`          | 通过 Intent 的 extras 获取可空的参数            |
| `by Activity.intentExtras(key, default)` | 通过 Intent 的 extras 获取含默认值的参数        |
| `by Activity.safeIntentExtras(key)`      | 通过 Intent 的 extras 获取人为保证非空的参数    |
| `dial(phoneNumber)`                      | 拨号                                            |
| `makeCall(phoneNumber)`                  | 打电话                                          |
| `sendSMS(phoneNumber, content)`          | 发短信                                          |
| `browse(url, [newTask])`                 | 打开网页                                        |
| `email(email, [subject], [text])`        | 发送邮件                                        |
| `installAPK(uri)`                        | 安装 APK                                        |
| `Intent.clearTask()`                     | 添加 FLAG_ACTIVITY_CLEAR_TASK 的 flag           |
| `Intent.clearTop()`                      | 添加 FLAG_ACTIVITY_CLEAR_TOP 的 flag            |
| `Intent.newDocument()`                   | 添加 FLAG_ACTIVITY_NEW_DOCUMENT 的 flag         |
| `Intent.excludeFromRecents()`            | 添加 FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS 的 flag |
| `Intent.multipleTask()`                  | 添加 FLAG_ACTIVITY_MULTIPLE_TASK 的 flag        |
| `Intent.newTask()`                       | 添加 FLAG_ACTIVITY_NEW_TASK 的 flag             |
| `Intent.noAnimation()`                   | 添加 FLAG_ACTIVITY_NO_ANIMATION 的 flag         |
| `Intent.noHistory()`                     | 添加 FLAG_ACTIVITY_NO_HISTORY 的 flag           |
| `Intent.singleTop()`                     | 添加 FLAG_ACTIVITY_SINGLE_TOP 的 flag           |
| `Intent.grantReadPermission()`           | 添加 FLAG_GRANT_READ_URI_PERMISSION 的 flag     |
| `Intent.clearTask()`                     | 添加 FLAG_ACTIVITY_CLEAR_TASK 的 flag           |
| `Intent.clearTask()`                     | 添加 FLAG_ACTIVITY_CLEAR_TASK 的 flag           |

### [Keyboard.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Keyboard.kt)

| 用法                                       | 作用             |
| ------------------------------------------ | ---------------- |
| `Activity/Fragment/View.showKeyboard()`    | 显示键盘         |
| `Activity/Fragment/View.hideKeyboard()`    | 隐藏键盘         |
| `Activity/Fragment/View.toggleKeyboard()`  | 切换键盘显示状态 |
| `Activity/Fragment/View.isKeyboardVisible` | 判断键盘是否可见 |
| `Activity/Fragment/View.keyboardHeight`    | 获取键盘高度     |

### [Lifecycle.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Lifecycle.kt)

| 用法                                     | 作用                                   |
| ---------------------------------------- | -------------------------------------- |
| `Application.doOnActivityLifecycle(...)` | 监听所有 Activity 的生命周期           |
| `LifecycleOwner.doOnLifecycle {...}`     | 监听当前 Activity 或 Fragment 生命周期 |
| `Fragment.doOnViewLifecycle {...}`       | 监听当前 Fragment 视图的生命周期       |

### [Logger.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Logger.kt)

| 用法                                  | 作用                                     |
| ------------------------------------- | ---------------------------------------- |
| `Any.TAG`                             | 获取对象的标签（不大于 23 个字符的类名） |
| `[Logger].logVerbose(message, [thr])` | 打印 Verbose 等级的日志                  |
| `[Logger].logDebug(message, [thr])`   | 打印 Debug 等级的日志                    |
| `[Logger].logInfo(message, [thr])`    | 打印 Info 等级的日志                     |
| `[Logger].logWarn(message, [thr])`    | 打印 Warn 等级的日志                     |
| `[Logger].logError(message, [thr])`   | 打印 Error 等级的日志                    |
| `[Logger].logVerWtf(message, [thr])`  | 打印 Wtf 等级的日志                      |
| `initLogger([isLoggable], [printer])` | 初始化 Logger (可选)                     |

### [MetaData.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/MetaData.kt)

| 用法                          | 作用                             |
| ----------------------------- | -------------------------------- |
| `applicationMetaDataOf(name)` | 获取 application 的 meta-data 值 |
| `activityMetaDataOf<T>(name)` | 获取 activity 的 meta-data 值    |
| `serviceMetaDataOf<T>(name)`  | 获取 service 的 meta-data 值     |
| `providerMetaDataOf<T>(name)` | 获取 provider 的 meta-data 值    |
| `receiverMetaDataOf<T>(name)` | 获取 receiver 的 meta-data 值    |

### [Network.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Network.kt)

| 用法                         | 作用                   |
| ---------------------------- | ---------------------- |
| `isNetworkAvailable`         | 判断网络是否可用       |
| `isWifiConnected`            | 判断网络是否是  Wifi   |
| `isMobileData`               | 判断网络是否是移动数据 |
| `isWifiEnabled`              | 判断 Wifi 是否打开     |
| `NetworkAvailableLiveData()` | 监听网络状态改变       |

### [Paths.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Paths.kt)

| 用法                           | 作用                         |
| ------------------------------ | ---------------------------- |
| `cacheDirPath`                 | 获取应用缓存路径（优先外存） |
| `externalCacheDirPath`         | 获取外存应用缓存路径         |
| `externalFilesDirPath`         | 获取外存应用文件路径         |
| `externalPicturesDirPath`      | 获取外存应用图片路径         |
| `externalMoviesDirPath`        | 获取外存应用视频路径         |
| `externalDownloadsDirPath`     | 获取外存应用下载路径         |
| `externalDocumentsDirPath`     | 获取外存应用文档路径         |
| `externalMusicDirPath`         | 获取外存应用音乐路径         |
| `externalPodcastsDirPath`      | 获取外存应用播客路径         |
| `externalRingtonesDirPath`     | 获取外存应用铃声路径         |
| `externalAlarmsDirPath`        | 获取外存应用闹铃路径         |
| `externalNotificationsDirPath` | 获取外存应用通知路径         |
| `internalCacheDirPath`         | 获取内存应用缓存路径         |
| `internalFileDirPath`          | 获取内存应用文件路径         |
| `internalPicturesDirPath`      | 获取内存应用图片路径         |
| `internalMoviesDirPath`        | 获取内存应用视频路径         |
| `internalDownloadsDirPath`     | 获取内存应用下载路径         |
| `internalDocumentsDirPath`     | 获取内存应用文档路径         |
| `internalMusicDirPath`         | 获取内存应用音乐路径         |
| `internalPodcastsDirPath`      | 获取内存应用播客路径         |
| `internalRingtonesDirPath`     | 获取内存应用铃声路径         |
| `internalNotificationsDirPath` | 获取内存应用通知路径         |
| `isExternalStorageWritable`    | 判断外存是否可读写           |
| `isExternalStorageReadable`    | 判断外存是否可读             |
| `isExternalStorageRemovable`   | 判断外存是否可移除           |

### [PdfDocument.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/PdfDocument.kt)

| 用法                                                  | 作用                           |
| ----------------------------------------------------- | ------------------------------ |
| `Bitmap/List<Bitmap>.toPdfDoc(pageWidth, pageHeight)` | Bitmap 转 PDF 文档             |
| `Bitmap/List<Bitmap>.toA4PdfDoc()`                    | Bitmap 转 A4 纸大小的 PDF 文档 |

### [Rescoures.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Rescoures.kt)

| 用法                                          | 作用                  |
| --------------------------------------------- | --------------------- |
| `View.getString(id)`                          | 根据 id 获取字符串    |
| `Context/Fragment/View.getDimension(id)`      | 根据 id 获取数字      |
| `Context/Fragment/View.getCompatColor(id)`    | 根据 id 获取颜色      |
| `Context/Fragment/View.getCompatDrawable(id)` | 根据 id 获取 Drawable |
| `Context/Fragment/View.getCompatFont(id)`     | 根据 id 获取字体      |
| `parseColor(colorString)`                     | 根据字符串获取颜色    |

### [Screen.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Screen.kt)

| 用法                             | 作用               |
| -------------------------------- | ------------------ |
| `screenWidth`                    | 获取屏幕宽度       |
| `screenHeight`                   | 获取屏幕高度       |
| `Activity/Fragment.isFullScreen` | 判断或设置是否全屏 |
| `Activity/Fragment.isLandscape`  | 判断或设置是否横屏 |
| `Activity/Fragment.isPortrait`   | 判断或设置是否竖屏 |

### [Share.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Share.kt)

| 用法                                     | 作用               |
| ---------------------------------------- | ------------------ |
| `shareText(text, [title])`               | 分享文字           |
| `shareImage(uri, [title])`               | 分享单张图片       |
| `shareImages(uris, [title])`             | 分享多张图片       |
| `shareTextAndImage(text, uri, [title])`  | 分享文字和单张图片 |
| `shareTextAndImages(text, uris,[title])` | 分享文字和多张图片 |
| `shareFile(text, [title], [mimeType])`   | 分享单个文件       |
| `shareFiles(text, [title], [mimeType])`  | 分享多个文件       |

### [SpannableStringBuilder.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/SpannableStringBuilder.kt)

| 用法                                                         | 作用                 |
| ------------------------------------------------------------ | -------------------- |
| `SpannableStringBuilder#size(size) {...}`                    | 设置大小             |
| `SpannableStringBuilder#alignCenter {...}`                   | 设置段落居中         |
| `SpannableStringBuilder#alignOpposite {...}`                 | 设置段落居右         |
| `SpannableStringBuilder#blur(radius, [style]) {...}`         | 设置模糊             |
| `SpannableStringBuilder#fontFamily(family) {...}`            | 设置字体系列         |
| `SpannableStringBuilder#typeface(typeface) {...}`            | 设置字体             |
| `SpannableStringBuilder#url(url) {...}`                      | 设置超链接           |
| `SpannableStringBuilder#bullet(gapWidth, [color]) {...}`     | 设置段落的列表标记   |
| `SpannableStringBuilder#quote([color]) {...}`                | 设置段落的引用线颜色 |
| `SpannableStringBuilder#leadingMargin(first, [rest]) {...}`  | 设置缩进             |
| `SpannableStringBuilder#append(drawable, [width], [height]) {...}` | 追加图片             |
| `SpannableStringBuilder#append(drawableId) {...}`            | 追加图片             |
| `SpannableStringBuilder#append(bitmap) {...}`                | 追加图片             |
| `SpannableStringBuilder#appendClickable(text, [color], [isUnderlineText]) {...}` | 追加可点击的文字     |
| `SpannableStringBuilder#appendClickable(drawable, [width], [height]) {...}` | 追加可点击的图片     |
| `SpannableStringBuilder#appendClickable(drawableId) {...}`   | 追加可点击的图片     |
| `SpannableStringBuilder#appendClickable(bitmap) {...}`       | 追加可点击的图片     |
| `SpannableStringBuilder#appendSpace(size, [color]) {...}`    | 追加空白             |


### [String.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/String.kt)

| 用法                              | 作用                               |
| --------------------------------- | ---------------------------------- |
| `randomUUIDString`                | 获取随机 UUID 字符串               |
| `Long.toFileSizeString()`         | 数字转文件大小字符串               |
| `Long.toShortFileSizeString()`    | 数字转精度位数较少的文件大小字符串  |
| `String.limitLength(length: Int)` | 限制字符长度                      |
| `String.isPhone()`                | 判断是否是手机号                   |
| `String.isDomainName()`           | 判断是否是域名                     |
| `String.isEmail()`                | 判断是否是邮箱                     |
| `String.isIP()`                   | 判断是否是 IP 地址                 |
| `String.isWebUrl()`               | 判断是否是网址                     |
| `String.isIDCard15()`             | 判断是否是 15 位身份证号码         |
| `String.isIDCard18()`             | 判断是否是 18 位身份证号码         |
| `String.isJson()`                 | 判断是否是 Json 字符串             |

### [SystemBars.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/SystemBars.kt)

| 用法                                                         | 作用                               |
| ------------------------------------------------------------ | ---------------------------------- |
| `Activity/Fragment.immerseStatusBar([lightMode]，[addBottomMargin])` | 沉浸式状态栏                       |
| `Activity/Fragment.transparentStatusBar()`                   | 透明状态栏                         |
| `Activity/Fragment.statusBarColor`                           | 获取或设置状态栏颜色               |
| `Activity/Fragment.isLightStatusBar`                         | 判断或设置状态栏是否为浅色模式     |
| `Activity/Fragment.isStatusBarVisible`                       | 判断或设置状态栏是否为显示         |
| `Activity/Fragment.statusBarHeight`                          | 获取状态栏高度                     |
| `View.addStatusBarHeightToMarginTop()`                       | 控件的顶部外边距增加状态栏高度     |
| `View.subtractStatusBarHeightToMarginTop()`                  | 控件的顶部外边距减少状态栏高度     |
| `View.addStatusBarHeightToPaddingTop()`                      | 控件的顶部内边距增加状态栏高度     |
| `View.subtractStatusBarHeightToPaddingTop()`                 | 控件的顶部内边距减少状态栏高度     |
| `Activity/Fragment.navigationBarColor`                       | 获取或设置状态栏颜色               |
| `Activity/Fragment.isLightNavigationBar`                     | 判断或设置状态栏是否为浅色模式     |
| `Activity/Fragment.isNavigationBarVisible`                   | 判断或设置状态栏是否为显示         |
| `Activity/Fragment.navigationBarHeight`                      | 获取状态栏高度                     |
| `View.addNavigationBarHeightToMarginBottom()`                | 控件的底部外边距增加虚拟导航栏高度 |
| `View.subtractNavigationBarHeightToMarginBottom()`           | 控件的底部外边距减少虚拟导航栏高度 |

### [TextView.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/TextView.kt)

| 用法                                                         | 作用                   |
| ------------------------------------------------------------ | ---------------------- |
| `TextView.textString`                                        | 获取文本字符串         |
| `TextView.isTextEmpty()`                                     | 判断文本是否为空       |
| `TextView.isTextNotEmpty()`                                  | 判断文本是否非空       |
| `TextView.isPasswordVisible`                                 | 判断或设置密码是否可见 |
| `TextView.addUnderline()`                                    | 添加下划线             |
| `TextView.startCountDown(lifecycleOwner, [secondInFuture], onTick, onFinish)` | 开启倒计时并修改文字   |
| `TextView.enableWhenOtherTextNotEmpty(textViews)`            | 当其它文本非空时可用   |
| `TextView.enableWhenOtherTextChanged(textViews) {...}`       | 当其它文本改变时可用   |

### [Threads.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Threads.kt)

| 用法                              | 作用         |
| --------------------------------- | ------------ |
| `mainThread([delayMillis]) {...}` | 在主线程运行 |

### [Toast.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Toast.kt)

| 用法                                  | 作用                          |
| ------------------------------------- | ----------------------------- |
| `Context/Fragment.toast(message)`     | 吐司                          |
| `Context/Fragment.longToast(message)` | 长吐司                        |
| `Toast.fixBadTokenException()`        | 修复 7.1 的 BadTokenException |

### [Uri.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/Uri.kt)

| 用法                                             | 作用                           |
| ------------------------------------------------ | ------------------------------ |
| `fileProviderAuthority`                          | 设置 FileProvider 的 authority |
| `File.toUri([authority])`                        | File 转 Uri                    |
| `Uri.update([block])`                            | 更新 Uri                       |
| `Uri.openFileDescriptor([mode]) {...}`           | 打开 Uri 的文件描述符          |
| `Uri.openInputStream {...}`                      | 打开 Uri 的输入流              |
| `Uri.openOutputStream {...}`                     | 打开 Uri 的输出流              |
| `Uri.loadThumbnail(width, height, [signal])`     | 加载 Uri 封面                  |
| `Uri.observeContentChange(lifecycleOwner) {...}` | 观察 Uri 内容改变              |
| `Uri.fileExtension`                              | 获取 Uri 的文件拓展名          |
| `Uri.mimeType`                                   | 获取 Uri 的 MIME 类型          |

### [View.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/View.kt)

| 用法                                                         | 作用                              |
| ------------------------------------------------------------ | --------------------------------- |
| `View/List<View>.doOnClick([clickIntervals], [isSharingIntervals])  {...}` | 设置点击事件                      |
| `View/List<View>.doOnLongClick([clickIntervals], [isSharingIntervals])  {...}` | 设置长按事件                      |
| `View.roundCorners`                                          | 设置圆角                          |
| `View?.isTouchedAt(x, y)`                                    | 判断控件是否在触摸位置上          |
| `View.findTouchedChild(view, x, y)`                          | 寻找触摸位置上的子控件            |
| `View.locationOnScreen`                                      | 获取控件在屏幕的位置              |
| `View.withStyledAttributes(set, attrs, [defStyleAttr], [defStyleRes]) {...}` | 获取自定义属性                    |
| `View.doOnApplyWindowInsets {...}`                           | 监听应用 WindowInsets             |
| `View.rootWindowInsetsCompat`                                | 获取根视图的 WinowInsetsCompat    |
| `View.windowInsetsControllerCompat`                          | 获取 WindowInsetsControllerCompat |

### [ViewModel.kt](https://github.com/DylanCaiCoding/Longan/blob/master/longan/src/main/java/com/dylanc/longan/ViewModel.kt)

| 用法                                                    | 作用                                              |
| ------------------------------------------------------- | ------------------------------------------------- |
| `by ComponentActivity/Fragment.applicationViewModels()` | 获取 Application 级别的 ViewModel 实例，支持 Hilt |