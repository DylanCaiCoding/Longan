# Longan

[English](https://github.com/DylanCaiCoding/Longan) | 中文

[![](https://www.jitpack.io/v/DylanCaiCoding/Longan.svg)](https://www.jitpack.io/#DylanCaiCoding/Longan) [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/Longan/blob/master/LICENSE)

Longan 是一个简化 Android 开发的 Kotlin 工具类集合，可以使代码更加简洁易读。

## Gradle

在根目录的 build.gradle 添加：

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

添加依赖：

```groovy
dependencies {
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.0.0'
    // Optional
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.0.0'
}
```

## Wiki

### 全部 APIs

- [Common APIs](https://github.com/DylanCaiCoding/Longan/wiki/All-Common-APIs)
- [Design APIs](https://github.com/DylanCaiCoding/Longan/wiki/All-Design-APIs)

### 部分用法

- [Activity](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Activity)
- [Dialogs](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Dialogs)
- [Fragment](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Fragment)
- [Intents](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Intents)
- [Logger](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Logger)
- [SystemBars](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-SystemBars)
- [Uri](https://github.com/DylanCaiCoding/Longan/wiki/Longan-%E2%80%93-Uri)

## 更新日志

[Releases](https://github.com/DylanCaiCoding/Longan/releases)

## 作者其它的库

- [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) —— 深度解耦标题栏或加载中、加载失败、无数据等视图
- [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX) —— 最全面的 ViewBinding 工具
- [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX) —— 让 MMKV 更加易用
- [ActivityResultLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher) —— 优雅地替代 `startActivityForResult()`

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
