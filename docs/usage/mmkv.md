# MMKV 用法

由于官方已经不推荐用 `SharedPreferences`，所以把 `SharedPreferences` 工具类改成用 `MMKV` 实现。

考虑到有些人可能会用官方的 `DataStore`，就另写了一个库 [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX) 作为可选项。

### 用法

添加依赖到 `build.gradle`：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding:MMKV-KTX:1.2.11'
}
```

让一个类实现 `MMKVOwner` 接口，即可通过 `by mmkvXXXX()` 方法将属性委托给 `MMKV`，例如：

```kotlin
object DataRepository : MMKVOwner {
  var isFirstLaunch by mmkvBool(default = true)
  var user by mmkvParcelable<User>()
}
```

设置或获取属性的值则调用对应的 encode 或 decode 方法，key 值为属性名。

支持以下类型：

| 方法               | 默认值 |
| ------------------ | ------ |
| `mmkvInt()`        | 0      |
| `mmkvLong()`       | 0L     |
| `mmkvBool()`       | false  |
| `mmkvFloat()`      | 0f     |
| `mmkvDouble()`     | 0.0    |
| `mmkvString()`     | /      |
| `mmkvStringSet()`  | /      |
| `mmkvBytes()`      | /      |
| `mmkvParcelable()` | /      |

在 `MMKVOwner` 的实现类可以获取 `kv` 对象进行删除值或清理缓存等操作：

```kotlin
kv.removeValueForKey(::isFirstLaunch.name)
kv.clearAll()
```

如果不同业务需要**区别存储**，可以重写 `kv` 属性来创建不同的 `MMKV` 实例：

```kotlin
object DataRepository : MMKVOwner {
  override val kv: MMKV = MMKV.mmkvWithID("MyID")
}
```

完整的用法可查看[单元测试](https://github.com/DylanCaiCoding/MMKV-KTX/blob/master/library/src/androidTest/java/com/dylanc/mmkv/MMKVTest.kt)代码。
