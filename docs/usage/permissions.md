# 权限用法

基于 [Activity Result API](https://developer.android.com/training/basics/intents/result?hl=zh-cn) 进行封装。

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

override fun onCreate(savedInstanceState: Bundle?) {
  // ...
  requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
}
```

## 请求多个权限

```kotlin
private val requestMultiplePermissionsLauncher = requestMultiplePermissionsLauncher(
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

override fun onCreate(savedInstanceState: Bundle?) {
  // ...
  requestMultiplePermissionsLauncher.launch(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
  )
}
```
