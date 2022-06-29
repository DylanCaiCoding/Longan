# ActivityResult 用法

[Activity Result API](https://developer.android.com/training/basics/intents/result?hl=zh-cn) 是官方用于替代 `startActivityForResult()` 和 `onActivityResult()` 的工具。不过 API 用起来有点繁琐，本库对其封装优化了用法。并且支持了更多的使用场景，如开启蓝牙、开启定位、跳转 Wifi 设置页面等。

**注意，为了和官方 API 的用法更加一致，从 `1.1.0` 版本开始，将原有的 `xxxxLauncher` 函数名改成了 `registerForXXXXResult`。**

## 替代 startActivityForResult()

```kotlin
private val startActivityLauncher = registerForStartActivityResult {
  if (it.resultCode == Result_OK) {
    // 处理结果
  }
}

startActivityLauncher.launch<SomeActivity>("id" to 5) 
```

## 替代 startIntentSenderForResult()

```kotlin
private val startIntentSenderLauncher = registerForStartIntentSenderResult {
  if (it.resultCode == Result_OK) {
    // 处理结果
  }
}

startIntentSenderLauncher.launch(intentSender) 
```

## 拍照预览

调用系统相册拍照后返回 Bitmap，仅仅用作展示。

```kotlin
private val takePicturePreviewLauncher = registerForTakePicturePreviewResult { bitmap ->
  if (bitmap != null) {
    imageView.setImageBitmap(bitmap)
  }
}

takePicturePreviewLauncher.launch()
```

## 拍照

```kotlin
private lateinit var uri: Uri
private val takePictureLauncher = registerForTakePictureResult { takeSuccess ->
  if (takeSuccess) {
    // 处理 uri
  }
}

// 保存到共享存储空间，可在相册中查看
uri = contentResovler.insertMediaImage()
takePictureLauncher.launch(uri)

// 保存到应用缓存目录
uri = takePictureLauncher.launchAndSaveToCache()
```

## 录像

```kotlin
private lateinit var uri: Uri
private val takeVideoLauncher = registerForTakeVideoResult {
  if (uri.size > 0) {
    // 处理 uri
  }
}

// 保存到共享存储空间，可在相册中查看
uri = contentResovler.insertMediaVideo()
takePictureLauncher.launch(uri)

// 保存到应用缓存目录
uri = takeVideoLauncher.launchAndSaveToCache()
```

## 选择单个图片或视频

有 `registerForPickContentResult {...}` 和 `registerForGetContentResult {...}` 两个方法可供选择，对应 Intent 的 action 分别是 `Intent.ACTION_PICK` 和 `Intent.ACTION_GET_CONTENT`。官方建议用 `Intent.ACTION_GET_CONTENT`，但是会跳转一个 Material Design 的选择文件页面，通常不符合需求，而用 `Intent.ACTION_PICK` 才会跳转相册页面。可以两个都试一下再做选择。

```kotlin
private val pickContentLauncher = registerForPickContentResult { uri ->
  if (uri != null) {
    // 处理 uri
  }
}

pickContentLauncher.launchForImage()
// pickContentLauncher.launchForVideo()
```

## 选择多个图片或视频

只有 `registerForGetMultipleContentsResult {...}` 可以选择，对应 Intent 的 action 是 `Intent.ACTION_GET_CONTENT`。`Intent.ACTION_PICK` 不支持多选。

```kotlin
private val getMultipleContentsLauncher = registerForGetMultipleContentsResult { uris ->
  if (uris.isNotEmpty()) {
    // 处理 uri 列表
  }
}

getMultipleContentsLauncher.launchForImage()
// pickContentLauncher.launchForVideo()
```

## 裁剪图片

```kotlin
private lateinit var outputUri: Uri
private val cropPictureLauncher = cropPictureLauncher { uri ->
  if (uri != null) {
    // 处理 uri
  }
}

cropPictureLauncher.launch(inputUri, "aspectX" to 1, "aspectY" to 1)
```

## 请求单个权限

```kotlin
private val requestPermissionLauncher = registerForRequestPermissionResult(
  onGranted = {
    // 已同意
  },
  onDenied = {
    // 拒绝且不再询问，可弹框引导用户到设置里授权该权限
    // 弹框提示后可调用 launchAppSettings() 方法跳到设置页
  },
  onShowRequestRationale = {
    // 拒绝了一次，可弹框解释为什么要获取该权限
    // 弹框提示后可调用 requestPermissionAgain() 方法重新请求
  })

requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
```

## 请求多个权限

```kotlin
private val requestMultiplePermissionsLauncher = registerForRequestMultiplePermissionsResult(
  onAllGranted = {
    // 已全部同意
  },
  onDenied = { deniedList ->
    // 部分权限已拒绝且不再询问，可弹框引导用户到设置里授权该权限
    // 弹框提示后可调用 launchAppSettings() 方法跳到设置页
  },
  onShowRequestRationale = { deniedList ->
    // 部分权限拒绝了一次，可弹框解释为什么要获取该权限
    // 弹框提示后可调用 requestDeniedPermissions() 方法请求拒绝的权限
  })

requestMultiplePermissionsLauncher.launch(
  Manifest.permission.ACCESS_FINE_LOCATION,
  Manifest.permission.READ_EXTERNAL_STORAGE,
)
```

## 启动系统设置的 App 详情页

```kotlin
private val appSettingsLauncher = registerForLaunchAppSettingsResult {
  // ...
}

appSettingsLauncher.launch()
```

## 启动通知的设置页

```kotlin
private val notificationSettingsLauncher = registerForLaunchNotificationSettingsResult {
  // ...
}

notificationSettingsLauncher.launch()
```

## 启动 Wifi 的设置页

```kotlin
private val wifiSettingsLauncher = registerForLaunchWifiSettingsResult {
  // ...
}

wifiSettingsLauncher.launch()
```

## 打开蓝牙

```kotlin
private val enableBluetoothLauncher = registerForEnableBluetoothResult { enabled ->
  if (enabled) {
    // 已开启蓝牙
  } else {
    // 可弹框解释用途后可调用 enableBluetooth() 方法再次开启
  }
}

enableBluetoothLauncher.launch()
```

## 打开定位

```kotlin
private val enableLocationLauncher = registerForEnableLocationResult { enabled ->
  if (enabled) {
    // 已开启定位
  } else {
    // 可弹框解释用途后可调用 enableLocation() 方法再次开启
  }
}

enableLocationLauncher.launch()
```

## 创建文档

```kotlin
private val createDocumentLauncher = createDocumentLauncher { uri ->
  if (uri != null) {
    // 处理 uri
  }
}

createDocumentLauncher.launch(filename)
```

## 打开单个文档

```kotlin
private val openDocumentLauncher = registerForOpenDocumentResult { uri ->
  if (uri != null) {
    // 处理 uri
  }
}

openDocumentLauncher.launch("application/*")
```

## 打开多个文档

```kotlin
private val openMultipleDocumentsLauncher = registerForOpenMultipleDocumentsResult { uris ->
  if (uris.isNotEmpty()) {
    // 处理 uri 列表
  }
}

openMultipleDocumentsLauncher.launch("application/*")
```

## 访问目录内容

```kotlin
private val openDocumentTreeLauncher = registerForOpenDocumentTreeResult { uri ->
  if (uri != null) {
    val documentFile = DocumentFile.fromTreeUri(context, uri)
    // 处理文档文件
  }
}

openDocumentTreeLauncher.launch(null)
```

## 选择联系人

```kotlin
private val pickContactLauncher = registerForPickContactResult { uri ->
  if (uri != null) {
    // 处理 uri
  }
}

pickContactLauncher.launch()
```