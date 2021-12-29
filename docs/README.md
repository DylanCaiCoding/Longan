# Longan

[![](https://www.jitpack.io/v/DylanCaiCoding/Longan.svg)](https://www.jitpack.io/#DylanCaiCoding/Longan) 
[![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/Longan/blob/master/LICENSE) 
[![GitHub Repo stars](https://img.shields.io/github/stars/DylanCaiCoding/Longan?style=social)](https://github.com/DylanCaiCoding/Longan)

Longan 是一个简化 Android 开发的 Kotlin 工具类集合，可以使代码更加简洁易读。

**目前有超过 300 个常用方法或属性，能有效提高开发效率**。每个用法都会思考很多，并且参考官方 KTX 库的命名规则和用法，用起来会更加的舒服。具体的实现代码也会优化几版，用尽可能简洁轻量的代码实现功能，有兴趣的可以读下源码。

本库会长期维护，有任何使用上的问题或者想要的功能都可以反馈。

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
    implementation 'com.github.DylanCaiCoding.Longan:longan:1.0.3'
    // Optional
    implementation 'com.github.DylanCaiCoding.Longan:longan-design:1.0.3'
}
```

## 更新日志

[Releases](https://github.com/DylanCaiCoding/Longan/releases)

## 作者其它的库

| 库                                                           | 简介                                           |
| ------------------------------------------------------------ | ---------------------------------------------- |
| [LoadingStateView](https://github.com/DylanCaiCoding/LoadingStateView) | 深度解耦标题栏或加载中、加载失败、无数据等视图 |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX)     | 最全面的 ViewBinding 工具                |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)                 | 用属性委托的方式使用 MMKV                  |

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
